import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from 'app/shared';
import { MovieComponent, MovieDetailComponent, MovieUpdateComponent, movieRoute } from './';

const ENTITY_STATES = [...movieRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [MovieComponent, MovieDetailComponent, MovieUpdateComponent],
  entryComponents: [MovieComponent, MovieUpdateComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MovieMovieModule {}
