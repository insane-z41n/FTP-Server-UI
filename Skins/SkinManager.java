package Skins;

import java.awt.Color;
import java.awt.Font;

public class SkinManager {
	
	public String fontType;
	public Color primary, secondary, tert, back;
	public Font titleFont, infoFont, plainInfoFont, buttonFont;
	
	public SkinManager() {
		//COLORS
		primary = Color.WHITE;
		secondary = Color.decode("#0F96D7");
		tert = Color.decode("#e0e0e0");
		back = Color.decode("#A9D2E2");
		
		//FONTS
		fontType = "";
		titleFont = new Font(fontType, Font.BOLD, 30);
		infoFont = new Font(fontType, Font.BOLD, 18);
		plainInfoFont = new Font(fontType, Font.PLAIN, 18);
		buttonFont = new Font(fontType, Font.PLAIN, 20);
	}
	
	//Set Colors.
	public void setPrimary(Color primary) {
		this.primary = primary;
	}
	public void setSecondary(Color secondary) {
		this.secondary = secondary;
	}
	public void setTert(Color tert) {
		this.tert = tert;
	}
	
	
}
