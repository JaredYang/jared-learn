package com.jared.core.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
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
        File file = new File(DATA_DIR);
        Directory directory = null;
        IndexWriter writer = null;
        try {
            directory = FSDirectory.open(new File(INDEX_DIR));
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_31, analyzer);
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            writer = new IndexWriter(directory, indexWriterConfig);
            indexDoc(writer, file);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void query(String queryString) {
        IndexReader indexReader = null;
        IndexSearcher indexSearch = null;
        String field = "content";
        try {
            indexReader = IndexReader.open(FSDirectory.open(new File(INDEX_DIR)));
            indexSearch = new IndexSearcher(indexReader);
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);
            QueryParser queryParser = new QueryParser(Version.LUCENE_31, field, analyzer);
            Query query = queryParser.parse(queryString);
            System.out.println("Searching for: " + query.toString(field));
            TopDocs results = indexSearch.search(query, 100);
            ScoreDoc[] hits = results.scoreDocs;
            for (int i = 0; i < hits.length; i++) {
                System.out.println("doc="+hits[i].doc+" score="+hits[i].score);
                Document doc = indexSearch.doc(hits[i].doc);
                String path = doc.get("path");
                if (path != null) {
                    System.out.println((i+1) + ". " + path);
                    String title = doc.get("content");
                    if (title != null) {
                        System.out.println("   content: " + doc.get("content"));
                    }
                } else {
                    System.out.println((i+1) + ". " + "No path for this document");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }finally {
            if (indexSearch != null) {
                try {
                    indexSearch.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (indexReader != null) {
                try {
                    indexReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        LuceneTest luceneTest = new LuceneTest();
        luceneTest.writer();
        luceneTest.query("Lucene");
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

    private void indexDoc(IndexWriter writer, File file) {
        if (file.canRead()) {
            if (file.isDirectory()) {
                String[] files = file.list();
                // an IO error could occur
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        indexDoc(writer, new File(file, files[i]));
                    }
                }
            } else {
                FileInputStream fis;
                try {
                    fis = new FileInputStream(file);

                } catch (FileNotFoundException e) {
                    return;
                }
                try {
                    Document document = new Document();
                    Field pathField = new Field("path", file.getPath(), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
                    pathField.setIndexOptions(FieldInfo.IndexOptions.DOCS_ONLY);
                    document.add(pathField);
                    NumericField modifiedField = new NumericField("modified");
                    modifiedField.setLongValue(file.lastModified());
                    document.add(modifiedField);
                    document.add(new Field("content", new BufferedReader(new InputStreamReader(fis, "UTF-8"))));
                    writer.addDocument(document);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (CorruptIndexException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

    }
}
