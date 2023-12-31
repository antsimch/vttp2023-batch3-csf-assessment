import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ShareNewsComponent } from './components/share-news/share-news.component';
import { TopTagsComponent } from './components/top-tags/top-tags.component';
import { ListNewsComponent } from './components/list-news/list-news.component';

@NgModule({
  declarations: [
    AppComponent,
    ShareNewsComponent,
    TopTagsComponent,
    ListNewsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
