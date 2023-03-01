<img src="/preview/Rick_and_Morty.svg" width="250"/>

# Rick and Morty

## Описание

Приложение позволяет посмотреть данные по героям, локациям и эпизодам небезысвестного мультсериала
"Rick and Morty".
<br>
Для первичной загрузки даных требуется интернет, в дальнейшем возможна автономная работа.
<br>
Используются данные открытого API: <https://rickandmortyapi.com/>

## Скриншоты
#### Герои
<img alt="Список героев" src="/preview/screenshots/001_characters_list.jpg" title="Список героев" width="300"/>
<img src="/preview/screenshots/002_characters_list_search.jpg" width="300" title="Поиск по героям" alt="Поиск по героям"/>
<br>
<img src="/preview/screenshots/003_character_item.jpg" width="300" title="Детали героя" alt="Детали героя"/>
<img src="/preview/screenshots/004_character_item_last_seen.jpg" width="300" title="Поиск последнего известного местонахождения" alt="Поиск последнего известного местонахождения"/>
<img src="/preview/screenshots/005_character_item_episodes.jpg" width="300" title="Список эпизодов с участием героя" alt="Список эпизодов с участием героя"/>

#### Локации
<img src="/preview/screenshots/006_locations_list.jpg" width="300" title="Список локаций" alt="Список локаций"/>
<img src="/preview/screenshots/007_location_item.jpg" width="300" title="Детали локации" alt="Детали локации"/>

#### Эпизоды
<img src="/preview/screenshots/008_episodes_list.jpg" width="300" title="Список эпизодов" alt="Список эпизодов"/>
<img src="/preview/screenshots/009_episode_item.jpg" width="300" title="Детали эпизода" alt="Детали эпизода"/>


#### Настройки
<img src="/preview/screenshots/010_settings_dark.jpg" width="300" title="Настройки, тёмная тема" alt="Настройки, тёмная тема"/>
<img src="/preview/screenshots/011_settings_lite.jpg" width="300" title="Настройки, светлая тема" alt="Настройки, светлая тема"/>

#### Ошибки
<img src="/preview/screenshots/012_error_dialog.jpg" width="300" title="Диалог ошибки" alt="Диалог ошибки"/>

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
* OkHttp3
* Glide
* Kotlinx Serialization
#### Прочее:
* Coroutines
* Flow
* Shared Preferences
* Pagination

## Планируемые доработки
* ежедневная нотификация со случайным героем
* переход к просмотру эпизода (Кинопоиск или аналог)
* добавление в избранное