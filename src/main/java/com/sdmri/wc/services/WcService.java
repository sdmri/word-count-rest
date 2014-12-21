package com.sdmri.wc.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author shiven.dimri
 * 
 */
@Service
public class WcService {

	Logger LOG = Logger.getLogger(WcService.class);

	private static final String[] STORE_FILE_NAMES = { "wordStore1.txt",
			"wordStore2.txt" };

	// Cache that has word as the key and its total count
	// We can move this to an external cache like redis etc. or
	// use Map-reduce to save it in HDFS
	private Map<String, Integer> cachedWordCount = new ConcurrentHashMap<>();

	private static final String WORD_DELIMITER = " ";

	/**
	 * Process text from store to calculate word counts. Will be used in
	 * subsequent calls for iteration
	 */
	@PostConstruct
	private void initializeCacheFromStore() {
		for (String fileName : STORE_FILE_NAMES) {
			BufferedReader reader = null;
			try {
				InputStream in = this.getClass().getClassLoader()
						.getResourceAsStream(fileName);
				reader = new BufferedReader(new InputStreamReader(in));
				String line = null;
				while ((line = reader.readLine()) != null) {
					// We tokenize each line on the delimeter to get all words
					// in a line
					String[] words = line.split(WORD_DELIMITER);
					for (String word : words) {
						Integer wordCount = cachedWordCount.get(word);
						if (wordCount == null) {
							// First occurrence of word text
							wordCount = 1;
						} else {
							// Increment occurrence of existing word
							wordCount++;
						}
						cachedWordCount.put(word, wordCount);
					}
				}
			} catch (IOException e) {
				LOG.error("Error reading from file");
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						LOG.warn("Could not close stream to file " + fileName);
					}
				}
			}
		}

	}

	public int getWordCountFromStore(String wordToSearchFor) {
		if (wordToSearchFor == null || wordToSearchFor.isEmpty()) {
			throw new IllegalArgumentException("Search string cannot be null");
		}
		return cachedWordCount.get(wordToSearchFor) != null ? cachedWordCount
				.get(wordToSearchFor) : 0;
	}
}
