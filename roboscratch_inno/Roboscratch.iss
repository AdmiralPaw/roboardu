; Имя приложения
#define   Name       "OmegaBot_IDE"
; Путь установки по стандарту
#define   Path 			 "Arduino\tools"
; Версия приложения
#define   Version    "1.0.0"
; Фирма-разработчик
#define   Publisher  "Omega-lab"
; Сафт фирмы разработчика
#define   URL        "https://omegabot.ru/"
; Имя исполняемого модуля
#define   ExeName    "OmegaBot_IDE"
; Путь до приложения
#define		Sourse	 "C:\Users\Jesus\roboardu"



;------------------------------------------------------------------------------
;   Параметры установки
;------------------------------------------------------------------------------
[Setup]

; Уникальный идентификатор приложения, 
;сгенерированный через Tools -> Generate GUID
AppId= {{8B598797-275D-4E06-83DA-CF5DAEE9DB23}

; Прочая информация, отображаемая при установке
AppName={#Name}
AppVersion={#Version}
AppPublisher={#Publisher}
AppPublisherURL={#URL}
AppSupportURL={#URL}
AppUpdatesURL={#URL}

; Путь установки по-умолчанию 
DefaultDirName={userdocs}\Arduino

; Имя группы в меню "Пуск"
DefaultGroupName={#Name}

; Каталог, куда будет записан собранный setup и имя исполняемого файла
OutputDir={#Sourse}\roboscratch_inno
OutputBaseFileName=OmegaBot_IDE-setup

; Файл иконки
SetupIconFile={#Sourse}\roboscratch_inno\icon.ico

; Параметры сжатия
Compression=lzma
SolidCompression=yes


AlwaysShowDirOnReadyPage=yes 
DirExistsWarning=no
UninstallFilesDir={app}\tools\{#Name}

;------------------------------------------------------------------------------
;   Устанавливаем языки для процесса установки
;------------------------------------------------------------------------------
[Languages] 
; Если нужно, необходимо указывать файлы лицензии
Name: "english"; MessagesFile: "compiler:Default.isl";
; LicenseFile: "License_ENG.txt"
Name: "russian"; MessagesFile: "compiler:Languages\Russian.isl";
; LicenseFile: "License_RUS.txt"

;------------------------------------------------------------------------------
;   Опциональность - меню выбора типа установки
;------------------------------------------------------------------------------
[Types]
Name: "full"; 																 Description: "Полная установка"
Name: "custom"; 															 Description: "Выборочная установка"; Flags: iscustom 

[Components]
Name: "programm"; 														 Description: "Установить {#Name}"; Types: full custom ;Flags: fixed

Name: "libs"; 																 Description: "Установить библиотеки"; Types: full 
Name: "libs\Adafruit_SSD1306"; 								 Description: "Adafruit_SSD1306"; Types: full
Name: "libs\AdafruitGFX"; 										 Description: "Adafruit-GFX"; Types: full
Name: "libs\I2Cdev"; 													 Description: "I2Cdev"; Types: full
Name: "libs\MPU9250";													 Description: "MPU9250"; Types: full
Name: "libs\SparkFun_ToF_Range_FinderVL6180";  Description: "SparkFun_ToF_Range_Finder-VL6180"; Types: full

;------------------------------------------------------------------------------
;   Файлы, которые надо включить в пакет установщика
;------------------------------------------------------------------------------
[Files]

; Прилагающиеся ресурсы
Source: "{#Sourse}\ArduinoLibraries\Adafruit_SSD1306-master\*"; Components: libs\Adafruit_SSD1306; DestDir: "{userdocs}\Arduino\libraries\Adafruit_SSD1306-master"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\Adafruit-GFX-Library-master\*"; Components: libs\AdafruitGFX; DestDir: "{userdocs}\Arduino\libraries\Adafruit-GFX-Library-master"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\I2Cdev\*"; Components: libs\I2Cdev; DestDir: "{userdocs}\Arduino\libraries\I2Cdev"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\MPU9250-master\*"; Components: libs\MPU9250; DestDir: "{userdocs}\Arduino\libraries\MPU9250-master"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\SparkFun_ToF_Range_Finder-VL6180_Arduino_Library-master\*"; Components: libs\SparkFun_ToF_Range_FinderVL6180; DestDir: "{userdocs}\Arduino\libraries\SparkFun_ToF_Range_Finder-VL6180_Arduino_Library-master"; Flags: recursesubdirs createallsubdirs ignoreversion

Source: "{#Sourse}\target\OmegaBot_IDE-tool.jar"; Components: programm; DestDir: "{app}\tools\{#Name}\tool"; Flags: ignoreversion


