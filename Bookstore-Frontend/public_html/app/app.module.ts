import { NgModule }             from '@angular/core';
import { BrowserModule }        from '@angular/platform-browser';
import { FormsModule }          from '@angular/forms';
import { HttpModule }           from '@angular/http';
import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MainComponent }        from './components/main/main.component';
import { IndexComponent }       from './components/index/index.component';
import { HeaderComponent }      from './components/header/header.component';
import { DashboardComponent }   from './components/dashboard/dashboard.component';
import { DashboardAdminComponent } from './components/dashboard/dashboard-admin.component';
import { DashboardUserComponent } from './components/dashboard/dashboard-user.component';
import { LoginComponent }       from './components/login/login.component';

import { UserService }          from './services/user.service';
import { RequireLoginGuard }            from './auth-guard';

const appRoutes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [RequireLoginGuard],
  },
  {
    path: '',
    component: IndexComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    routing
  ],
  declarations: [
    MainComponent,
    HeaderComponent,
    DashboardComponent,
    IndexComponent,
    LoginComponent,
    DashboardUserComponent,
    DashboardAdminComponent
  ],
  providers: [
    UserService,
    RequireLoginGuard,
  ],
  bootstrap: [ MainComponent ]
})

export class AppModule {
}