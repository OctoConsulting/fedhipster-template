/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { HeroComponentsPage, HeroDeleteDialog, HeroUpdatePage } from './hero.page-object';

const expect = chai.expect;

describe('Hero e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let heroUpdatePage: HeroUpdatePage;
  let heroComponentsPage: HeroComponentsPage;
  let heroDeleteDialog: HeroDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Heroes', async () => {
    await navBarPage.goToEntity('hero');
    heroComponentsPage = new HeroComponentsPage();
    await browser.wait(ec.visibilityOf(heroComponentsPage.title), 5000);
    expect(await heroComponentsPage.getTitle()).to.eq('Heroes');
  });

  it('should load create Hero page', async () => {
    await heroComponentsPage.clickOnCreateButton();
    heroUpdatePage = new HeroUpdatePage();
    expect(await heroUpdatePage.getPageTitle()).to.eq('Create or edit a Hero');
    await heroUpdatePage.cancel();
  });

  it('should create and save Heroes', async () => {
    const nbButtonsBeforeCreate = await heroComponentsPage.countDeleteButtons();

    await heroComponentsPage.clickOnCreateButton();
    await promise.all([
      heroUpdatePage.setNameInput('name'),
      heroUpdatePage.setAffilitionInput('affilition'),
      heroUpdatePage.setEyeColorInput('eyeColor'),
      heroUpdatePage.setHairColorInput('hairColor'),
      heroUpdatePage.setGenderInput('gender'),
      heroUpdatePage.setStatusInput('status'),
      heroUpdatePage.setYearInput('5'),
      heroUpdatePage.setFirstAppearanceInput('firstAppearance'),
      heroUpdatePage.setUniverseInput('universe'),
      heroUpdatePage.setIntelligenceInput('5'),
      heroUpdatePage.setStrengthInput('5'),
      heroUpdatePage.setSpeedInput('5'),
      heroUpdatePage.setDurabilityInput('5'),
      heroUpdatePage.setPowerInput('5'),
      heroUpdatePage.setCombatInput('5'),
      heroUpdatePage.setTotalInput('5')
    ]);
    expect(await heroUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await heroUpdatePage.getAffilitionInput()).to.eq('affilition', 'Expected Affilition value to be equals to affilition');
    expect(await heroUpdatePage.getEyeColorInput()).to.eq('eyeColor', 'Expected EyeColor value to be equals to eyeColor');
    expect(await heroUpdatePage.getHairColorInput()).to.eq('hairColor', 'Expected HairColor value to be equals to hairColor');
    expect(await heroUpdatePage.getGenderInput()).to.eq('gender', 'Expected Gender value to be equals to gender');
    expect(await heroUpdatePage.getStatusInput()).to.eq('status', 'Expected Status value to be equals to status');
    expect(await heroUpdatePage.getYearInput()).to.eq('5', 'Expected year value to be equals to 5');
    expect(await heroUpdatePage.getFirstAppearanceInput()).to.eq(
      'firstAppearance',
      'Expected FirstAppearance value to be equals to firstAppearance'
    );
    expect(await heroUpdatePage.getUniverseInput()).to.eq('universe', 'Expected Universe value to be equals to universe');
    expect(await heroUpdatePage.getIntelligenceInput()).to.eq('5', 'Expected intelligence value to be equals to 5');
    expect(await heroUpdatePage.getStrengthInput()).to.eq('5', 'Expected strength value to be equals to 5');
    expect(await heroUpdatePage.getSpeedInput()).to.eq('5', 'Expected speed value to be equals to 5');
    expect(await heroUpdatePage.getDurabilityInput()).to.eq('5', 'Expected durability value to be equals to 5');
    expect(await heroUpdatePage.getPowerInput()).to.eq('5', 'Expected power value to be equals to 5');
    expect(await heroUpdatePage.getCombatInput()).to.eq('5', 'Expected combat value to be equals to 5');
    expect(await heroUpdatePage.getTotalInput()).to.eq('5', 'Expected total value to be equals to 5');
    await heroUpdatePage.save();
    expect(await heroUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await heroComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Hero', async () => {
    const nbButtonsBeforeDelete = await heroComponentsPage.countDeleteButtons();
    await heroComponentsPage.clickOnLastDeleteButton();

    heroDeleteDialog = new HeroDeleteDialog();
    expect(await heroDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Hero?');
    await heroDeleteDialog.clickOnConfirmButton();

    expect(await heroComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
