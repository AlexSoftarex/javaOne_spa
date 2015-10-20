package controllers;

import java.sql.SQLException;

import views.html.index;
import views.html.mainPage;
import play.mvc.*;

public class PageController extends Controller {

    public static Result mainPage() throws SQLException {

	return ok(index.render("Your new application is ready.", mainPage.render()));
    }
}
