package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
public class JdbcTemplateMemberRepository implements MemberRepository {
    // ***** 스프링 권장 스타일 시작 *****
    private final JdbcTemplate jdbcTemplate;
    public JdbcTemplateMemberRepository(DataSource dataSource) { // DataSource 인젝션
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    // ***** 스프링 권장 스타일 끝 *****

    /*
    확실히 아래 사용자 정의 메소드와 관련해서 순수 JDBC에 있는 것과 비교해 보았을 때
    훨씬 간략한 코드로 사용된다는 것을 알 수 있다!
     */
    @Override
    public Member save(Member member) {
        /*
        SimpleJdbcInsert을 활용해서 간단하게 회원 저장을 수행할 수 있음
         */
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }
    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny(); // optioanl 반환
    }
    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny(); // optioanl 반환
    }
    private RowMapper<Member> memberRowMapper() {
        // 람다 스타일
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}