package com.gems.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ApplicationContext {
	private static final String LOCATOR_PROPERTIES_FILE_NAME = "/locators.properties";
	private static final String APPLICATION_CONFIG_PROPERTIES_FILE_NAME = "/applicationConfig.properties";
	private static final String REMOTE_EXECUTION_KEY = "remoteExecution";
	private static final String DEFAULT_BROWSER_KEY = "browser";
	private static final String MAX_RETRY_COUNT_KEY = "maxRetryCount";
	private static ApplicationContext instance = new ApplicationContext();
	private static Properties locatorProps = new Properties();
	private static Properties appConfigProps = new Properties();

	private ApplicationContext() {
		// Do-nothing..Do not allow to initialize this class from outside
	}

	public static ApplicationContext getInstance() {
		return instance;
	}

	public Properties getLocatorProps() {
		if (locatorProps.isEmpty()) {
			locatorProps = loadProperties(LOCATOR_PROPERTIES_FILE_NAME);
		}
		return locatorProps;
	}

	public Properties getAppConfigProps() {
		if (appConfigProps.isEmpty()) {
			appConfigProps = loadProperties(APPLICATION_CONFIG_PROPERTIES_FILE_NAME);
		}
		return appConfigProps;
	}

	public void loadDefaultProperties() {
		if (locatorProps.isEmpty()) {
			locatorProps = loadProperties(LOCATOR_PROPERTIES_FILE_NAME);
		}
		if (appConfigProps.isEmpty()) {
			appConfigProps = loadProperties(APPLICATION_CONFIG_PROPERTIES_FILE_NAME);
		}
	}

	public Properties loadProperties(final String fileName) {
		InputStream inputStream = null;
		Properties props = new Properties();
		try {
			String propFileName = fileName;
			inputStream = getClass().getResourceAsStream(propFileName);
			// inputStream =
			// getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				props.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '"
						+ propFileName + "' not found in the classpath");
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		}
		return props;
	}

	public boolean isRemoteExecution() {
		boolean isRemoteExecution = false;
		if (appConfigProps.containsKey(REMOTE_EXECUTION_KEY)) {
			isRemoteExecution = Boolean.parseBoolean(appConfigProps
					.getProperty(REMOTE_EXECUTION_KEY));
		}
		return isRemoteExecution;
	}

	public String getDefaultBrowser() {
		String browser = null;
		if (appConfigProps.containsKey(DEFAULT_BROWSER_KEY)) {
			browser = appConfigProps.getProperty(DEFAULT_BROWSER_KEY);
		}
		return browser;
	}

	public String getApplicationUrl() {
		return appConfigProps.getProperty("application.base.url");
	}

	public int getMaxRetryCount() {
		return Integer
				.parseInt(appConfigProps.getProperty(MAX_RETRY_COUNT_KEY));
	}

	public String getUserName() {
		return appConfigProps.getProperty("user.name");
	}
	public String getOktUserName() {
		return appConfigProps.getProperty("user.email");
	}
	public String getOktPwd() {
		return appConfigProps.getProperty("user.password");
	}
	public String getEqptId() {
		return appConfigProps.getProperty("eqptId");
	}
	

	public String getPassword() {
		return appConfigProps.getProperty("");
	}

}
