package com.jared.core.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-7-12
 * Time: 上午10:32
 * To change this template use File | Settings | File Templates.
 */
public class LuceneTest {

    private static final String INDEX_DIR = "F:/lucene/index/";
    private static final String DATA_DIR = "F:/lucene/data/";

    public LuceneTest() {

    }

    private void writer() {
        File indexDir = new File(INDEX_DIR);
        File dataDir = new File(DATA_DIR);
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
        File[] files = dataDir.listFiles();
        IndexWriter indexWriter = null;
        try {
            indexWriter = new IndexWriter(FSDirectory.open(indexDir), analyzer, IndexWriter.MaxFieldLength.LIMITED);

            if (files != null && files.length > 0) {
                for (File f : files) {
                    Document document = new Document();
                    document.add(new Field("path", f.getPath(), Field.Store.YES, Field.Index.ANALYZED));
                    document.add(new Field("content", new FileReader(f)));
                    indexWriter.addDocument(document);
                }
            }

            indexWriter.optimize();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (indexWriter != null) {
                try {
                    indexWriter.close();
                } catch (IOException e) {

                }
            }
        }
    }

    private void query(String queryString) {
        File indexDir = new File(INDEX_DIR);
        IndexReader indexReader = null;
        Query query = null;
        try {
            indexReader = IndexReader.open(FSDirectory.open(indexDir), true);
            Searcher searcher = new IndexSearcher(indexReader);
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
            QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "content", analyzer);
            query = parser.parse(queryString);
            TopScoreDocCollector topScoreDocCollector = TopScoreDocCollector.create(1000, false);
            searcher.search(query, topScoreDocCollector);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            indexReader.clone();
        }

    }

    public static void main(String[] args) {
        LuceneTest luceneTest = new LuceneTest();
        //luceneTest.writer();
        luceneTest.query("java");
    }

    private String getFileContent(String filePath) {
        StringBuffer buffer = new StringBuffer();
        String line;
        try {
            FileReader fileReader = new FileReader(new File(filePath));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            if ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
