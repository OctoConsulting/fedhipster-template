import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from 'app/shared';
import { HeroComponent, HeroDetailComponent, HeroUpdateComponent, heroRoute } from './';

const ENTITY_STATES = [...heroRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [HeroComponent, HeroDetailComponent, HeroUpdateComponent],
  entryComponents: [HeroComponent, HeroUpdateComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppHeroModule {}
