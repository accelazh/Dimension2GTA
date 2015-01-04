package gameDisplayProcessor;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.*;

public class SubstitudeLayout implements LayoutManager {

	private Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
	
	
	public void addLayoutComponent(String arg0, Component arg1) {
		// TODO Auto-generated method stub

	}

	public void layoutContainer(Container arg0) {
		// TODO Auto-generated method stub

	}

	public Dimension minimumLayoutSize(Container arg0) {
		// TODO Auto-generated method stub
		return new Dimension(screenSize.width,screenSize.height-30);
	}

	public Dimension preferredLayoutSize(Container arg0) {
		// TODO Auto-generated method stub
		return new Dimension(screenSize.width,screenSize.height-30);
	}

	public void removeLayoutComponent(Component arg0) {
		// TODO Auto-generated method stub

	}

	public String toString()
	{
		return "SubstitudeLayout";
	}
}
