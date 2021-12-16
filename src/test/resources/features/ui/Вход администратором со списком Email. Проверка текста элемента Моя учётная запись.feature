#language: ru

Функция: Вход Администратором со списком Email. Проверка текста элемента "Моя учётная запись"

  Предыстория:
    Пусть Имеется список E-Mail адресов "СПИСОК_EMAIL_АДРЕСОВ":
      | Адрес            | По умолчанию | Уведомления |
      | first@email.com  | true         | true        |
      | second@email.com | false        | true        |
      | third@email.com  | false        | true        |
      | fourth@email.com | false        | false       |
    Пусть В системе есть пользователь "ПОЛЬЗОВАТЕЛЬ" с параметрами:
      | Администратор                | true                                                             |
      | Статус                       | Активирован                                                      |
      | Уведомления о новых событиях | Только для объектов, которые я отслеживаю или в которых участвую |
      | E-Mail                       | СПИСОК_EMAIL_АДРЕСОВ                                             |
    И Открыт браузер на странице "/login"

  @ui
  Сценарий: Вход Администратором со списком Email. Проверка текста элемента "Моя учётная запись"
    Если Авторизоваться как пользователь "ПОЛЬЗОВАТЕЛЬ"
    Тогда Текст элемента Моя учётная запись - "Моя учётная запись"