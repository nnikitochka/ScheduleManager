package ru.nnedition.schedule.telegram

import ru.nnedition.schedule.module.ScheduleModule
import ru.nnedition.schedule.telegram.objects.chat.Chat
import ru.nnedition.schedule.telegram.objects.ParseMode
import ru.nnedition.schedule.telegram.objects.user.User

/**
 * Абстрактный класс, представляющий Telegram-бота, связанного с модулем расписания.
 *
 * <p>Обеспечивает базовую инфраструктуру для отправки сообщений, регистрации и отслеживания пользователей.</p>
 *
 * <p>Используется в связке с {@link ScheduleModule} для интеграции функционала модуля с Telegram.</p>
 *
 * @property token - токен авторизации Telegram-бота
 */
abstract class TelegramBot(
    val token: String,
) {
    /**
     * Ссылка на модуль, зарегистрированный в Telegram-боте.
     *
     * <p>Устанавливается при вызове {@link #register} и используется для связи между ботом и модулем.</p>
     */
    lateinit var module: ScheduleModule
        protected set

    /**
     * Мапа с пользователями, взаимодействующими с ботом.
     *
     * <p>Ключом является цифирный айди пользователя Telegram, а значением - объект {@link User},
     * содержащий информацию о пользователе. Может использоваться для хранения состояния диалогов, прав доступа и т.д.</p>
     */
    val users: MutableMap<Long, User> = HashMap()

    /**
     * Абстрактный метод для отправки сообщения пользователю.
     *
     * <p>Метод отправляет сообщение в указанный чат с заданным содержанием и форматом разметки.</p>
     *
     * @param chat - объект {@link Chat}, представляющий чат получателя(ей) сообщения
     * @param content - текст сообщения, которое будет отправлено
     * @param parseMode - режим форматирования сообщения (по умолчанию - MARKDOWNV2)
     */
    abstract fun sendMessage(chat: Chat, content: String, parseMode: ParseMode = ParseMode.MARKDOWNV2)

    /**
     * Абстрактный метод регистрации бота.
     *
     * <p>Вызывается при активации модуля.</p>
     *
     * @param module - экземпляр {@link ScheduleModule}, который будет связан с этим ботом
     */
    abstract fun register(module: ScheduleModule)

    /**
     * Абстрактный метод отключения модуля от бота.
     *
     * <p>Вызывается при деактивации модуля. Должен выполнять очистку ресурсов, отписку от событий и т.п.</p>
     */
    abstract fun unregister()
}