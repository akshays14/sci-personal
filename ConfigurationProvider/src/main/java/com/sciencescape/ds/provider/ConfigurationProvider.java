/**
 *
 */
package com.sciencescape.ds.provider;

import java.io.File;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sciencescape.ds.provider.utils.Utilities;

/**
 * @author akshay
 *
 */
public class ConfigurationProvider {

	/**!< path of global configuration file */
	private String globalFile;
	/**!< path of local configuration file */
	private String localFile;
	/**!< ResourceBundle object to handle user messages */
	private final ResourceBundle messages;
	/**!< Logger object */
	private static Logger logger =
			LoggerFactory.getLogger(ConfigurationProvider.class);

	public ConfigurationProvider(String globalFile, String localFile)
			throws ProviderException {
		this.globalFile = globalFile;
		this.localFile = localFile;

		/* initialize user-message resource-bundle */
		messages = Utilities.configureUserMessages();

		/* if globalFile is provided, make sure it exists */
		if (new File(globalFile).isFile()) {
			throw new ProviderException(messages.getString("file_doesnt_exist"),
					globalFile);
		}
		//logger.info(messages.getString("in_hbase_table"), inputHBaseTable);
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
