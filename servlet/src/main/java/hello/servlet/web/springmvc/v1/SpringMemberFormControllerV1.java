package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


// 해당 애노테이션이 @Component 를 포함하고 있기에 스프링이 자동으로 스프링빈으로 등록.
// 스프링 MVC 에서 애노테이션 기반 컨트롤러로 인식.
@Controller
public class SpringMemberFormControllerV1 {

    // 요청정보를 매핑. 해당 URL에 호출되면 이 메서드가 호출됨.
    // 애노테이션 기반으로 동작하기 때문에, 메서드의 이름은 임의로 지어도 된다.
    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}
