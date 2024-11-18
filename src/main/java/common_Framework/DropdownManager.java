package common_Framework;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DropdownManager {
	RemoteWebDriver driver;
	private String item;
	private String expected;

	public void selectItem(List<WebElement> dropdownlistElement, String expecteditem) throws Exception {
		// System.out.println(dropdownlistElement.size());
		for (int i = 0; i < dropdownlistElement.size(); i++) {
			item = dropdownlistElement.get(i).getText();
			if (item.equalsIgnoreCase(expecteditem)) {
				dropdownlistElement.get(i).click();
				Thread.sleep(2000);
				break;
				// System.out.println("dropdown item selected");
			}
		}
	}

	public void selectItem(List<WebElement> dropdownListElement, List<WebElement> dropdownTextElement,
			String expecteditem) throws Exception {
		Thread.sleep(3000);
		System.out.println(dropdownListElement.size());
		System.out.println(dropdownTextElement.size());
		for (int i = 0; i < dropdownListElement.size(); i++) {
			item = dropdownTextElement.get(i).getText();
			if (item.trim().equalsIgnoreCase(expecteditem)) {
				dropdownListElement.get(i).click();
				Thread.sleep(2000);
				// System.out.println("dropdown item selected");
			}
		}
	}

	// To select the item from the Drop-down items
	public void selectItem(WebElement dropdown, List<WebElement> dropdownListElement,
			List<WebElement> dropdownTextElement, String expectedTitle, String expecteditem) throws Exception {
		for (int i = 0; i < dropdownTextElement.size(); i++) {
			String title = dropdownTextElement.get(i).getText();
			if (title.trim().equalsIgnoreCase(expectedTitle)) {
				dropdown.click();
				for (int j = 0; j < dropdownListElement.size(); j++) {
					// driver.findElement(By.xpath("(//div[contains(@class,'dropdown-trigger')])[1]")).click();
					String item = dropdownListElement.get(j).getAttribute("aria-label");
					if (item.trim().equalsIgnoreCase(expecteditem)) {
						dropdownListElement.get(j).click();
						Thread.sleep(2000);
						// System.out.println("dropdown item selected");
						break;
					}
				}
			}
		}
	}

	// To select required radio-button
	public void selectRadioButton(List<WebElement> radioButton, List<WebElement> radioButtonLabel,
			String expectedRadioButtonLabel) {
		for (int i = 0; i < radioButton.size(); i++) {
			String actualRadioButtonLabel = radioButtonLabel.get(i).getText();
			if (expectedRadioButtonLabel.equalsIgnoreCase(actualRadioButtonLabel)) {
				radioButton.get(i).click();
				break;
			}
		}
	}

}
