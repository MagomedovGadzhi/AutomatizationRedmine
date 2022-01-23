#language: ru

Функция: Видимость проекта. Приватный проект. Администратор.

  Предыстория:
    Пусть В системе есть пользователь "АДМИН" с параметрами:
      | Администратор | true        |
      | Статус        | Активирован |
    Пусть В системе есть проект "ПРИВАТНЫЙ_ПРОЕКТ" с параметрами:
      | Общедоступный | false |
    И Открыт браузер на главной странице

  @ui
  @smoke
  Сценарий: Видимость проекта. Приватный проект. Администратор.
    Если На странице "Меню страницы" нажать на элемент "Войти"
    И Авторизоваться как пользователь "АДМИН"
    Тогда На странице "Домашняя страница" отображается элемент "Имя страницы"
    Если На странице "Меню страницы" нажать на элемент "Проекты"
    Тогда На странице "Проекты" отображается элемент "Имя страницы"
    И В спике проектов отображается проект "ПРИВАТНЫЙ_ПРОЕКТ"