package com.wikia.webdriver.common.remote.operations;

import com.wikia.webdriver.common.core.helpers.User;
import com.wikia.webdriver.common.logging.PageObjectLogging;
import com.wikia.webdriver.common.remote.Discussions;
import com.wikia.webdriver.common.remote.context.CategoryContext;
import com.wikia.webdriver.common.remote.context.CreateCategoryContext;
import com.wikia.webdriver.elements.mercury.components.discussions.common.category.CategoryPill;
import lombok.AllArgsConstructor;
import org.openqa.selenium.WebDriver;

import java.rmi.RemoteException;
import java.util.ArrayList;

@AllArgsConstructor(staticName = "using")
public class DiscussionsCategoryOperations {

  private final User user;

  private final WebDriver driver;

  public CategoryPill.Data createCategory(String categoryName) {
    return createCategory(categoryName, extractSiteId());
  }

  public CategoryPill.Data createCategory(String categoryName, String siteId) {
    return createCategory(CreateCategoryContext.defaultContextUsing(siteId, categoryName));
  }

  private String extractSiteId() {
    return Discussions.extractSiteIdFromMediaWikiUsing(driver);
  }

  public CategoryPill.Data createCategory(CreateCategoryContext context) {
    return new CreateCategory(user).execute(context);
  }

  public void deleteCategory(final String siteId, CategoryPill.Data data) {
    deleteCategory(CategoryContext.defaultContextUsing(siteId, data));
  }

  public void deleteCategory(CategoryContext context) {
    new DeleteCategory(user).execute(context);
  }

  public ArrayList<CategoryPill.Data> getCategoriesFromSite(final String siteId, User user) {
    return new Categories(user).execute(CreateCategoryContext.defaultContextUsing(siteId, ""));
  }

  public void deleteAllCategories(final String siteId, User user) {
    ArrayList<CategoryPill.Data> categories = getCategoriesFromSite(siteId, user);
    for (CategoryPill.Data category : categories) {
      PageObjectLogging.logInfo("Deleting category: " + category.getName());
      deleteCategory(siteId, category);
    }
  }

  public void renameCategory(CategoryContext context) {
    new RenameCategory(user).execute(context);
  }
}
