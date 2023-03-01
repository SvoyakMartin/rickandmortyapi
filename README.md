<img src="/preview/Rick_and_Morty.svg" width="250"/>

# Rick and Morty

## Описание

Приложение позволяет посмотреть данные по героям, локациям и эпизодам небезысвестного мультсериала
"Rick and Morty". Для первичной загрузки даных требуется интернет, в дальнейшем возможна автономная работа.
Используются данные открытого API: <https://rickandmortyapi.com/>

## Скриншоты
![Список героев](preview/screenshots/001_characters_list.jpg)
![Поиск по героям](preview/screenshots/002_characters_list_search.jpg)
![Детали героя](preview/screenshots/003_character_item.jpg)
![Поиск последнего известного местонахождения](preview/screenshots/004_character_item_last_seen.jpg)
![Список эпизодов с участием героя](preview/screenshots/005_character_item_episodes.jpg)
![Список локаций](preview/screenshots/006_locations_list.jpg)
![Детали локации](preview/screenshots/007_location_item.jpg)
![Список эпизодов](preview/screenshots/008_episodes_list.jpg)
![Детали эпизода](preview/screenshots/009_episode_item.jpg)
![Настройки, тёмная тема](preview/screenshots/010_settings_dark.jpg)
![Настройки, светлая тема](preview/screenshots/011_settings_lite.jpg)
![Диалог ошибки](preview/screenshots/012_error_dialog.jpg)

## Используемые библиотеки и технологии
#### Паттерны:
* Clean Architecture
* MVVM
#### Навигация:
* Navigation Component
#### Внедрение зависимостей:
* Dagger 2
#### Базы данных:
* Room
#### Сеть и изображения:
* Retrofit
* Glide
* Kotlinx Serialization
#### Прочее:
* Coroutines
* Flow
* 