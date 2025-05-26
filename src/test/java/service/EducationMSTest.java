package service;

import com.infoa.educationms.entities.PersonalInfo;
import com.infoa.educationms.entities.Student;
import com.infoa.educationms.entities.User;
import com.infoa.educationms.repository.*;
import com.infoa.educationms.service.EducationMSImp;
import org.junit.jupiter.api.extension.ExtendWith;      // @ExtendWith
import org.mockito.InjectMocks;                         // @InjectMocks
import org.mockito.Mock;                                // @Mock
import org.mockito.junit.jupiter.MockitoExtension;      // Mockito 对 JUnit 5 的支持
import org.junit.jupiter.api.Test;                      // @Test 注解
import static org.junit.jupiter.api.Assertions.*;       // assertEquals / assertTrue 等断言
import static org.mockito.Mockito.*;                    // when / verify / any() 等 Mockito 静态方法

import com.infoa.educationms.queries.ApiResult;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EducationMSTest {
    @InjectMocks
    private EducationMSImp educationMS;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PersonalInfoRepository personalInfoRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private TakeRepository takeRepository;


    @Test
    public void testGetPersonalInfo_userExists() {
        // 创建 user 模拟对象
        int userId = 1;
        User mockUser = new Student();
        mockUser.setUserId(userId);
        mockUser.setPersonalInfoId(100);

        PersonalInfo mockInfo = new PersonalInfo();
        mockInfo.setName("Alice");

        // 假设数据库中有对应的 user 和 personalInfo
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(personalInfoRepository.findById(100)).thenReturn(Optional.of(mockInfo));

        // 调用方法
        ApiResult result = educationMS.getPersonalInfo(userId);

        // 验证结果
        assertTrue(result.result);
        assertEquals("查询成功", result.message);
        assertEquals(mockInfo, result.return_data);
    }

    @Test
    public void testGetPersonalInfo_userNotFound() {
        // 模拟 user 不存在
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        ApiResult result = educationMS.getPersonalInfo(999);

        assertFalse(result.result);
        assertEquals("用户不存在", result.message);
    }

    @Test
    public void testUpdatePersonalInfo_success() {
        // 创建 user 和 personalInfo 模拟对象
        int userId = 1;
        User mockUser = new Student();
        mockUser.setUserId(userId);
        mockUser.setPersonalInfoId(100);

        PersonalInfo newInfo = new PersonalInfo();
        newInfo.setName("Bob");
        newInfo.setPersonalInfoId(100);

        // 假设数据库中有对应的 user 和 personalInfo
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(personalInfoRepository.findById(100)).thenReturn(Optional.of(new PersonalInfo()));

        // 调用方法
        ApiResult result = educationMS.updatePersonalInfo(userId, newInfo);

        // 验证结果
        assertTrue(result.result);
        assertEquals("个人信息更新成功", result.message);
    }

    @Test
    public void testQuerySection() {
        // 模拟查询课程信息
        when(sectionRepository.findAll()).thenReturn(List.of());

        ApiResult result = educationMS.querySection();

        assertTrue(result.result);
        assertEquals("查询成功", result.message);
        assertNotNull(result.return_data);
    }

}
