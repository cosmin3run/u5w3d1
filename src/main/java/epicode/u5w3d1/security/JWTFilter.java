package epicode.u5w3d1.security;

import epicode.u5w3d1.entities.Employee;
import epicode.u5w3d1.exceptions.UnauthorizedException;
import epicode.u5w3d1.services.EmployeeService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private EmployeeService employeeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException{
        //Controllare se la richiesta ha Auth Header
        String autHeader = request.getHeader("Authorization");
        if (autHeader == null || !autHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Please insert token");

        //Estrarre il token dal header
        String accessToken = autHeader.substring(7);
        //verifico la signature se viene manipolata o scaduto
        jwtTools.verifyToken(accessToken);

        //Cerco l'id per trovare l'utente nel db
        String id = jwtTools.extractIdFromToken(accessToken);
        Employee employee = employeeService.findById(UUID.fromString(id));

        //Avvisare spring che l'utente è autenticato

        Authentication authentication = new UsernamePasswordAuthenticationToken(employee, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
    //Disabilita il filtro per richieste di tipo login e register

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        //Spiego al programma in che situazioni non attivare il filtro
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }

}
