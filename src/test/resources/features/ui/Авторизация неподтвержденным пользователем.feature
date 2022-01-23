#language: ru

Функция: Авторизация неподтвержденным пользователем.

  Предыстория:
    Пусть В системе есть пользователь "ПОЛЬЗОВАТЕЛЬ" с параметрами:
      | Администратор | false                                                                                 |
      | Статус        | Зарегестрирован, но еще не подтвердил почтовый ящик или не актвирован администратором |

    И Открыт браузер на главной странице

  @ui
  Сценарий: Авторизация неподтвержденным пользователем.
    Если На странице "Меню страницы" нажать на элемент "Войти"
    И Авторизоваться как пользователь "ПОЛЬЗОВАТЕЛЬ"
    Тогда На странице "Меню страницы" отображается элемент "Домашняя страница"
    И На странице "Меню страницы" отображается элемент "Проекты"
    И На странице "Меню страницы" отображается элемент "Помощь"
    И На странице "Меню страницы" отображается элемент "Войти"
    И На странице "Меню страницы" отображается элемент "Регистрация"
    И На странице "Страница авторизации" отображается элемент "Сообщение" с текстом "Ваша учётная запись создана и ожидает подтверждения администратора."
    И На странице "Меню страницы" не отображается элемент "Моя страница"