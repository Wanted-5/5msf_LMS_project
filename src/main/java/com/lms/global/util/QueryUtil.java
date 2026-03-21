package com.lms.global.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class QueryUtil {

    // 모든 쿼리를 모아두는 배열
    private static final Map<String, String> queries = new HashMap<>();

    // 팀원 파일 모아둔 배열
    private static final String[] QUERIES_FILES = {
            "users-queries.xml",
            "city-queries.xml",
            "section-queries.xml",
            "Comment-querys.xml",
            "quiz-queries.xml",
            "mafia-queries.xml"
    };


    // XML에서 쿼리를 로드하는 정적 블록
    static {
        for (String file : QUERIES_FILES) {
            loadQueriesFile(file);
        }
    }

    /**
     * 📌 XML 파일에서 쿼리를 읽어오는 메서드
     */
    private static void loadQueriesFile(String fileName) {
        try {
            // 클래스 로더를 통해 "users-quiz-queries.xml" 파일을 InputStream으로 가져옴
            InputStream inputStream = QueryUtil.class.getClassLoader().getResourceAsStream(fileName);

            // InputStream이 null인 경우, 즉 파일을 찾지 못한 경우 예외 발생
            if (inputStream == null) {
                System.out.println("⚠️ [안내] " + fileName + " 파일이 아직 없어 로딩을 건너뜁니다.");
                return;
            }

            // DocumentBuilderFactory를 사용하여 DocumentBuilder 인스턴스를 생성
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // InputStream으로부터 XML 문서를 파싱하여 Document 객체 생성
            Document document = builder.parse(inputStream);
            // 문서의 구조를 정규화 (노드의 표준 형태로 변환)
            document.getDocumentElement().normalize();
            // "query" 태그를 가진 모든 노드를 가져옴
            NodeList nodeList = document.getElementsByTagName("query");

            // 각 "query" 노드를 반복하여 처리
            for (int i = 0; i < nodeList.getLength(); i++) {
                // 현재 노드를 Element로 캐스팅
                Element queryElement = (Element) nodeList.item(i);
                // "id" 속성 값을 가져옴
                String key = queryElement.getAttribute("key");
                // 쿼리의 텍스트 내용을 가져와서 공백을 제거
                String sql = queryElement.getTextContent().trim();
                // ID를 키로, SQL 쿼리를 값으로 하여 맵에 저장
                queries.put(key, sql);
            }
            System.out.println("✅ " + fileName + " 쿼리 로딩 완료!");

        } catch (Exception e) {
            // 예외 발생 시 RuntimeException으로 감싸서 다시 던짐
            throw new RuntimeException("쿼리 로딩 중 오류 발생", e);
        }
    }


    /**
     * 📌 특정 쿼리 ID로 SQL 가져오기
     * @param key XML에서 정의한 query의 id
     * @return SQL 쿼리 문자열
     */
    public static String getQuery(String key) {
        return queries.get(key);
    }
}
