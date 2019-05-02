package com.ardublock.ui.ControllerConfiguration;

import java.awt.*;
import com.ardublock.ui.ControllerConfiguration.ControllerImage;

public class СontrollerСonfiguration extends Component {

    Container everythingVisible = new Container();
    //private ControllerImage controllerImage = new ControllerImage();
    public ControllerImage controllerImage = new ControllerImage();

    public СontrollerСonfiguration()
    {

        super();
        setBackground(new Color(200,200,200));
    }

    public initControllerConfiguration(){
        everythingVisible.add(controllerImage);
    }

}
