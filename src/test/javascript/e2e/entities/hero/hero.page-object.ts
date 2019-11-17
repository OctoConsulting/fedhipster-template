import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class HeroComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-hero div table .btn-danger'));
  title = element.all(by.css('jhi-hero div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getText();
  }
}

export class HeroUpdatePage {
  pageTitle = element(by.id('jhi-hero-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  affilitionInput = element(by.id('field_affilition'));
  eyeColorInput = element(by.id('field_eyeColor'));
  hairColorInput = element(by.id('field_hairColor'));
  genderInput = element(by.id('field_gender'));
  statusInput = element(by.id('field_status'));
  yearInput = element(by.id('field_year'));
  firstAppearanceInput = element(by.id('field_firstAppearance'));
  universeInput = element(by.id('field_universe'));
  intelligenceInput = element(by.id('field_intelligence'));
  strengthInput = element(by.id('field_strength'));
  speedInput = element(by.id('field_speed'));
  durabilityInput = element(by.id('field_durability'));
  powerInput = element(by.id('field_power'));
  combatInput = element(by.id('field_combat'));
  totalInput = element(by.id('field_total'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setAffilitionInput(affilition) {
    await this.affilitionInput.sendKeys(affilition);
  }

  async getAffilitionInput() {
    return await this.affilitionInput.getAttribute('value');
  }

  async setEyeColorInput(eyeColor) {
    await this.eyeColorInput.sendKeys(eyeColor);
  }

  async getEyeColorInput() {
    return await this.eyeColorInput.getAttribute('value');
  }

  async setHairColorInput(hairColor) {
    await this.hairColorInput.sendKeys(hairColor);
  }

  async getHairColorInput() {
    return await this.hairColorInput.getAttribute('value');
  }

  async setGenderInput(gender) {
    await this.genderInput.sendKeys(gender);
  }

  async getGenderInput() {
    return await this.genderInput.getAttribute('value');
  }

  async setStatusInput(status) {
    await this.statusInput.sendKeys(status);
  }

  async getStatusInput() {
    return await this.statusInput.getAttribute('value');
  }

  async setYearInput(year) {
    await this.yearInput.sendKeys(year);
  }

  async getYearInput() {
    return await this.yearInput.getAttribute('value');
  }

  async setFirstAppearanceInput(firstAppearance) {
    await this.firstAppearanceInput.sendKeys(firstAppearance);
  }

  async getFirstAppearanceInput() {
    return await this.firstAppearanceInput.getAttribute('value');
  }

  async setUniverseInput(universe) {
    await this.universeInput.sendKeys(universe);
  }

  async getUniverseInput() {
    return await this.universeInput.getAttribute('value');
  }

  async setIntelligenceInput(intelligence) {
    await this.intelligenceInput.sendKeys(intelligence);
  }

  async getIntelligenceInput() {
    return await this.intelligenceInput.getAttribute('value');
  }

  async setStrengthInput(strength) {
    await this.strengthInput.sendKeys(strength);
  }

  async getStrengthInput() {
    return await this.strengthInput.getAttribute('value');
  }

  async setSpeedInput(speed) {
    await this.speedInput.sendKeys(speed);
  }

  async getSpeedInput() {
    return await this.speedInput.getAttribute('value');
  }

  async setDurabilityInput(durability) {
    await this.durabilityInput.sendKeys(durability);
  }

  async getDurabilityInput() {
    return await this.durabilityInput.getAttribute('value');
  }

  async setPowerInput(power) {
    await this.powerInput.sendKeys(power);
  }

  async getPowerInput() {
    return await this.powerInput.getAttribute('value');
  }

  async setCombatInput(combat) {
    await this.combatInput.sendKeys(combat);
  }

  async getCombatInput() {
    return await this.combatInput.getAttribute('value');
  }

  async setTotalInput(total) {
    await this.totalInput.sendKeys(total);
  }

  async getTotalInput() {
    return await this.totalInput.getAttribute('value');
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class HeroDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-hero-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-hero'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
