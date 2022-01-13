#language: ru

Функция: Проверка универсальности UI-шагов

  Предыстория:
    Пусть Открыт браузер на главной странице

  @ui
  Сценарий: Проверка универсальности UI-шагов
    Если На странице "Меню страницы" нажать на элемент "Войти"
    И На странице "Страница авторизации" в поле "Логин" ввести текст "admin"
    И На странице "Страница авторизации" в поле "Пароль" ввести текст "admin123"
    И На странице "Страница авторизации" нажать на элемент "Вход"
    И На странице "Меню страницы" нажать на элемент "Администрирование"
    И На странице "Администрирование" нажать на элемент "Пользователи"
    И На странице "Пользователи" нажать на элемент "Создано"
    И На странице "Пользователи" тексты элементов "Даты создания" отсортированы по дате по убыванию