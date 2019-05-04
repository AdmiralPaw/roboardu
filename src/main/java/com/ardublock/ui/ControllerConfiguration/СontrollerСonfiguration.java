package com.ardublock.ui.ControllerConfiguration;

import java.awt.*;
import com.ardublock.ui.ControllerConfiguration.ControllerImage;

public class СontrollerСonfiguration extends Component {

    Container everythingVisible = new Container();
    public ControllerImage controllerImage = new ControllerImage();

    public СontrollerСonfiguration()
    {

        super();
        setBackground(new Color(200,200,200));
    }

    public void initControllerConfiguration(){
        everythingVisible.add(controllerImage);
    }

}
