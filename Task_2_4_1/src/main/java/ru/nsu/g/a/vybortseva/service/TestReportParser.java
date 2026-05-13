package ru.nsu.g.a.vybortseva.service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import ru.nsu.g.a.vybortseva.model.TestResult;

/**
 * Parser for Gradle XML test reports to extract success/failure statistics.
 */
public class TestReportParser {

    /**
     * Parses all XML reports in the project build directory.
     */
    public TestResult parse(File taskDir, String taskId) {
        File reportsDir = new File(taskDir, "build/test-results/test");
        int total = 0;
        int passed = 0;
        int failed = 0;
        int skipped = 0;

        if (reportsDir.exists() && reportsDir.isDirectory()) {
            File[] files = reportsDir.listFiles((dir, name) -> name.endsWith(".xml"));
            if (files != null) {
                for (File file : files) {
                    try {
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document doc = builder.parse(file);

                        Element testsuite = doc.getDocumentElement();
                        total += Integer.parseInt(testsuite.getAttribute("tests"));
                        failed += Integer.parseInt(testsuite.getAttribute("failures"));
                        failed += Integer.parseInt(testsuite.getAttribute("errors"));
                        skipped += Integer.parseInt(testsuite.getAttribute("skipped"));

                    } catch (Exception e) {
                        System.err.println("parsing error");
                    }
                }
            }
        }

        passed = total - failed - skipped;
        return new TestResult(taskId, true, passed, total, "OK");
    }
}
