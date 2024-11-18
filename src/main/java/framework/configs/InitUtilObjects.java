package framework.configs;

import common.utilsAll.DBUtils;
import common.utilsAll.DataLoaders;
import common_Framework.AssertManager;
import common_Framework.BaseTest;
import common_Framework.UIUtils;

public class InitUtilObjects {
	private UIUtils uiutil;
	private AssertManager assertManager;
	private DBUtils dbUtil;
	private DataLoaders data ;
	
	public InitUtilObjects() {
		uiutil = new UIUtils();
		assertManager = new AssertManager();
		dbUtil = new DBUtils();
		data = new DataLoaders();
	}
	
	public void setupTestObj() {
		BaseTest.utilObj.get().uiutil.setupTestObj();
		BaseTest.utilObj.get().assertManager.setupTestObj();
		BaseTest.utilObj.get().data.setupTestObj();
	}
	
	public UIUtils getUIUtils() {
		return uiutil;
	}
	
	public DataLoaders getDataLoaders() {
		return data;
	}
	
	public AssertManager getAssertManager() {
		return assertManager;
	}
	
	public DBUtils getDBUtils() {
		return dbUtil;
	}

}
