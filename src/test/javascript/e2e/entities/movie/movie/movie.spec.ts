/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { MovieComponentsPage, MovieDeleteDialog, MovieUpdatePage } from './movie.page-object';

const expect = chai.expect;

describe('Movie e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let movieUpdatePage: MovieUpdatePage;
  let movieComponentsPage: MovieComponentsPage;
  let movieDeleteDialog: MovieDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Movies', async () => {
    await navBarPage.goToEntity('movie');
    movieComponentsPage = new MovieComponentsPage();
    await browser.wait(ec.visibilityOf(movieComponentsPage.title), 5000);
    expect(await movieComponentsPage.getTitle()).to.eq('Movies');
  });

  it('should load create Movie page', async () => {
    await movieComponentsPage.clickOnCreateButton();
    movieUpdatePage = new MovieUpdatePage();
    expect(await movieUpdatePage.getPageTitle()).to.eq('Create or edit a Movie');
    await movieUpdatePage.cancel();
  });

  it('should create and save Movies', async () => {
    const nbButtonsBeforeCreate = await movieComponentsPage.countDeleteButtons();

    await movieComponentsPage.clickOnCreateButton();
    await promise.all([movieUpdatePage.setNameInput('name'), movieUpdatePage.setAverageInput('5')]);
    expect(await movieUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await movieUpdatePage.getAverageInput()).to.eq('5', 'Expected average value to be equals to 5');
    await movieUpdatePage.save();
    expect(await movieUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await movieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Movie', async () => {
    const nbButtonsBeforeDelete = await movieComponentsPage.countDeleteButtons();
    await movieComponentsPage.clickOnLastDeleteButton();

    movieDeleteDialog = new MovieDeleteDialog();
    expect(await movieDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Movie?');
    await movieDeleteDialog.clickOnConfirmButton();

    expect(await movieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
