package automatization.redmine.cucumber.validators;

import java.util.Arrays;
import java.util.List;

public class RoleParametersValidator {

    public static void validateRolePermissions(List<String> permissions) {
        List<String> allowedPermissions = Arrays.asList("Создание проекта",
                "Редактирование проектов",
                "Закрывать / открывать проекты",
                "Выбор модулей проекта",
                "Управление устаниками",
                "Управление версиями",
                "Создание подпроектов",
                "Управление общими запросами",
                "Сохранение запросов",
                "Просмотр сообщений",
                "Отправка сообщений",
                "Редактирование сообщений",
                "Редактирование собственных сообщений",
                "Удаление сообщений",
                "Удаление собственных сообщений",
                "Управление форумами",
                "Просмотр календаря",
                "Просмотр документов",
                "Добавить документы",
                "Редактировать документы",
                "Удалить документы",
                "Просмотр файлов",
                "Управление файлами",
                "Просмотр диаграммы Ганта",
                "Просмотр задач",
                "Добавление задач",
                "Редактирование задач",
                "Редактировать свои задачи",
                "Копирование задач",
                "Управление связыванием задач",
                "Управление подзадачами",
                "Установление видимости (общая/частная) для задач",
                "Установление видимости (общая/частная) для собственных задач",
                "Добавление примечаний",
                "Редактирование примечаний",
                "Редактирование собственных примечаний",
                "Просмотр приватных комментариев",
                "Размещение приватных комментариев",
                "Удаление задач",
                "Просмотр списка наблюдателей",
                "Добавление наблюдателей",
                "Удаление наблюдателей",
                "Импорт задач",
                "Управление категориями задач",
                "Просмотр новостей",
                "Управление новостями",
                "Комментирование новостей",
                "Просмотр изменений хранилища",
                "Просмотр хранилища",
                "Изменение файлов в хранилище",
                "Управление связанными задачами",
                "Управление хранилищем",
                "Просмотр трудозатрат",
                "Учёт трудозатрат",
                "Редактирование учёта времени",
                "Редактирование собственного учёта времени",
                "Управление типами действий для проекта",
                "Учитывать время других пользователей",
                "Импорт трудозатрат",
                "Просмотр Wiki",
                "Просмотр истории Wiki",
                "Экспорт wiki-страниц",
                "Редактирование wiki-страниц",
                "Переименование wiki-страниц",
                "Удаление wiki-страниц",
                "Удаление прикреплённых файлов",
                "Блокирование wiki-страниц",
                "Управление Wiki");

        boolean allPermissionsAreValid = allowedPermissions.containsAll(permissions);
        if (!allPermissionsAreValid) {
            throw new IllegalArgumentException("Среди переданных прав роли есть недопустимые права");
        }
    }
}