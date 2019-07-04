package com.ardublock.translator.block.roboarduBlock.sensors;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class temperatureSensor extends TranslatorBlock {
    public temperatureSensor(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException {

        TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
        String pin = translatorBlock.toCode();
        String tempSens="tempSens";
        translator.addHeaderFile("OneWire.h");
        translator.addDefinitionCommand("OneWire "+tempSens+"(" + pin + ");");
        
        translator.addDefinitionCommand("double catchTemperature(OneWire &oneW){\n" +
"  byte data[2]; // Место для значения температуры\n" +
"  \n" +
"  oneW.reset(); // Начинаем взаимодействие со сброса всех предыдущих команд и параметров\n" +
"  oneW.write(0xCC); // Даем датчику DS18b20 команду пропустить поиск по адресу. В нашем случае только одно устрйоство \n" +
"  oneW.write(0x44); // Даем датчику DS18b20 команду измерить температуру. Само значение температуры мы еще не получаем - датчик его положит во внутреннюю память\n" +
"  \n" +
"  delay(1000); // Микросхема измеряет температуру, а мы ждем.  \n" +
"  \n" +
"  oneW.reset(); // Теперь готовимся получить значение измеренной температуры\n" +
"  oneW.write(0xCC); \n" +
"  oneW.write(0xBE); // Просим передать нам значение регистров со значением температуры\n" +
"\n" +
"  // Получаем и считываем ответ\n" +
"  data[0] = oneW.read(); // Читаем младший байт значения температуры\n" +
"  data[1] = oneW.read(); // А теперь старший\n" +
"\n" +
"  // Формируем итоговое значение: \n" +
"  //    - сперва \"склеиваем\" значение, \n" +
"  //    - затем умножаем его на коэффициент, соответсвующий разрешающей способности (для 12 бит по умолчанию - это 0,0625)\n" +
"  return ((data[1] << 8) | data[0]) * 0.0625;\n" +
"}");



        return codePrefix + "catchTemperature(&" + tempSens + ")" + codeSuffix;
    }
}
