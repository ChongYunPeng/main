# A0132785Yunused
###### src\doordonote\ui\UI.java
``` java
    /* This method was unused in the final product as we used org.apache.commons.lang's
     * WordUtil class' wrap() method for wrapping text of long tasks
     */
	public static String wrapText(String text) {
		StringBuilder sb = new StringBuilder(text);

		int x = 0;
		while (x + 50 < sb.length() && (x = sb.lastIndexOf(" ", x + 50)) != -1) {
			sb.replace(x, x + 1, "\n");
		}
		return sb.toString();
	}

}
```
