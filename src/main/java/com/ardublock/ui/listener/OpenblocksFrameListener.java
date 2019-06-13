package com.ardublock.ui.listener;

public interface OpenblocksFrameListener {
	 void didSave();
	 void didLoad();
	 void didGenerate(String source);
	 void didVerify(String source);
}
