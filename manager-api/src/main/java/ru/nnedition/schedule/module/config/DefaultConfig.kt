package ru.nnedition.schedule.module.config

import ru.nnedition.configuration.YMLConfig
import ru.nnedition.configuration.annotations.ConfigField
import ru.nnedition.schedule.module.ScheduleModule

open class DefaultConfig(module: ScheduleModule) : YMLConfig("${module.dataFolder}/config.yml") {
    @ConfigField("warn_unknown_command.enabled")
    val warnUnknownCommand = true
    @ConfigField("warn_unknown_command.message")
    val unknownCommandMessage = "⚠ Неизвестная команда!"

    @ConfigField("bot_info")
    val botInfo = listOf(
        "🧐 *Насколько актуально расписание?*",
        " *»* Бот полностью автономен и берёт всю информацию с сайта. Обновление расписания происходит каждые %schedule_update_delay% минут, поэтому оно всегда актуально! Кстати, последняя проверка расписания была %last_schedule_update% назад.",
        "",
        "🫠 *Есть ли тут расписание для моей группы?*",
        " *»* Если ваша группа есть на сайте расписания, то и тут она обязательно будет :>",
        "",
        "❓ *Остались вопросы или есть проблемы?*",
        " *»* Если ответа на ваш вопрос нету в /help, то не стесняйтесь задавать вопросы или рассказывать о своих проблемах разработчику @nnikitochka.",
        "",
        "😃 *Пишешь код на kotlin или java?*",
        " *»* Присоединяйся к разработке!",
        "",
        "🫀 Текущая версия: %app_version%",
        "💼 Версия модуля: %module_version%",
        "👥 Сейчас ботом пользуются: %users_count%",
    )
    
    @ConfigField("building.format")
    val buildingsDataFormat = " » {short_name} - {full_name} ({address})"

    @ConfigField("commands.start.text")
    val startCommandText = listOf(
        "👋 Добро пожаловать! Вот несколько самых часто задаваемых вопросов, после их прочтения выберите группу:",
        "",
        "%bot_info%",
    )

    @ConfigField("commands.help.text")
    val helpCommandText = listOf(
        "📒 *Команды бота:*",
        " *»* /schedule - посмотреть расписание",
        " *»* /options - настроить бота",
        " *»* /report - отправить отчёт об ошибке",
        " *»* /info - немного информации о боте",
        " *»* /help - помощь",
        "",
        "🏘 *Список корпусов:*",
        "%buildings%"
    )
}