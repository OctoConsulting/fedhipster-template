import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'movie',
        loadChildren: './movie/movie/movie.module#MovieMovieModule'
      },
      {
        path: 'hero',
        loadChildren: './hero/hero.module#AppHeroModule'
      },
      {
        path: 'hero',
        loadChildren: './hero/hero.module#AppHeroModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppEntityModule {}
