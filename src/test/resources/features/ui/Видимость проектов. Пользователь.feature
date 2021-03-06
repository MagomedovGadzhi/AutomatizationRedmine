#language: ru

Функция: Видимость проектов. Пользователь.

  Предыстория:
    Пусть В системе есть пользователь "ПОЛЬЗОВАТЕЛЬ" с параметрами:
      | Администратор | false       |
      | Статус        | Активирован |
    Пусть В системе есть роль "РОЛЬ" с правами:
      | Просмотр задач |
    Пусть В системе есть проект "ПУБЛИЧНЫЙ_ПРОЕКТ_1" с параметрами:
      | Общедоступный | true |
    Пусть В системе есть проект "ПРИВАТНЫЙ_ПРОЕКТ_2" с параметрами:
      | Общедоступный | false |
    Пусть В системе есть проект "ПРИВАТНЫЙ_ПРОЕКТ_3" с параметрами:
      | Общедоступный | false |
    Пусть Пользователь "ПОЛЬЗОВАТЕЛЬ" имеет доступ к проекту "ПРИВАТНЫЙ_ПРОЕКТ_3" с ролью "РОЛЬ"
    И Открыт браузер на главной странице

  @ui
  @smoke
  Сценарий: Видимость проектов. Пользователь.
    Если На странице "Меню страницы" нажать на элемент "Войти"
    И Авторизоваться как пользователь "ПОЛЬЗОВАТЕЛЬ"
    Тогда На странице "Домашняя страница" отображается элемент "Имя страницы"
    Если На странице "Меню страницы" нажать на элемент "Проекты"
    Тогда На странице "Проекты" отображается элемент "Имя страницы"
    И В спике проектов отображается проект "ПУБЛИЧНЫЙ_ПРОЕКТ_1"
    И В спике проектов не отображается проект "ПРИВАТНЫЙ_ПРОЕКТ_2"
    И В спике проектов отображается проект "ПРИВАТНЫЙ_ПРОЕКТ_3"