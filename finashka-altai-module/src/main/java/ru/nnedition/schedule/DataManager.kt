package ru.nnedition.schedule

import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import ru.nnedition.schedule.module.ScheduleModule
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class DataManager(module: ScheduleModule) : DataHandler(module) {
    val scheduleSite = "http://barnaul.fa.ru/lessons/"
    val bellScheduleSite = "https://altai.fa.ru/for-students/shedule/"

    private val buildingsRegex = Regex("""^(\S+)\s*-\s*(.+?)\s*\((.+?)\)$""")

    override fun updateBuildings() {
        val request = Request.Builder().url(scheduleSite).get().build()
        OkHttpClient().newCall(request).execute().use { response ->
            val html = response.body?.string() ?: return
            val document = Jsoup.parse(html)
            val corpsList = mutableListOf<Building>()
            val corpsTable = document.select("table.simple")
            corpsTable.select("tr td").forEach { element ->
                val corpsInfo = element.text()
                if (corpsInfo.isBlank()) return@forEach

                this.buildingsRegex.find(corpsInfo)?.let {
                    val (shortName, fullName, address) = it.destructured

                    corpsList.add(
                        Building(shortName, fullName, address)
                    )
                }
            }

            this.module.schedule.buildings.apply {
                clear()
                addAll(corpsList)
            }
        }
    }

    override fun updateGroups() {
        val request = Request.Builder().url(scheduleSite).get().build()
        OkHttpClient().newCall(request).execute().use { response ->
            val selectRegex = """<select\s+name="groupname"[^>]*>(.*?)</select>""".toRegex(RegexOption.DOT_MATCHES_ALL)
            val html = response.body?.string() ?: return
            val selectMatch = selectRegex.find(html)?.groups?.get(1)?.value ?: return
            val optionRegex = """<option\s+value=([^>]+)>[^<]+</option>""".toRegex()

            val groups = optionRegex.findAll(selectMatch)
                .mapNotNull { it.groups[1]?.value?.removeSurrounding("\"") }
                .filter { !it.contains("?") }

            this.module.schedule.groups.apply {
                clear()
                addAll(groups)
            }
        }
    }

    override fun updateSchedule() {

    }

    override fun updateBellSchedule() {
        val request = Request.Builder().url(bellScheduleSite).get().build()
        OkHttpClient().newCall(request).execute().use { response ->
            val html = response.body?.string() ?: return
            val doc = Jsoup.parse(html)

            val table = doc.select("p:contains(Расписание звонков:) + table").first() ?: run {
                println("Таблица с расписанием звонков не найдена.")
                return
            }

            val timeFormat = DateTimeFormatter.ofPattern("H:mm")
            val lessons = mutableListOf<Pair<LocalTime, LocalTime>>()

            table.select("tr").forEach { row ->
                val cells = row.select("td")
                if (cells.size != 2) return@forEach

                val startText = cells[0].text()
                val endText = cells[1].text()

                if (
                    startText.contains("Начало", true)
                    || endText.contains("Окончание", true)
                ) return@forEach

                try {
                    val start = LocalTime.parse(startText, timeFormat)
                    val end = LocalTime.parse(endText, timeFormat)
                    lessons.add(Pair(start, end))
                } catch (e: Exception) {
                    println("[ПРЕДУПРЕЖДЕНИЕ] Невозможно разобрать строку: $startText - $endText")
                }
            }

            // Проверим, отсортированы ли пары
            val isSorted = lessons.zipWithNext().all { it.first.first <= it.second.first }

            if (!isSorted) {
                lessons.sortBy { it.first }
            }

            println("Расписание звонков:")

            //this.module.schedule.bellSchedule.apply {
                lessons.forEachIndexed { index, lesson ->
                    println("${index+1} $lesson")
                }
            //}
        }
    }
}