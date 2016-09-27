# aip-a2

## Front-end
#### Prerequisities: nodeJS & npm

> curl -sL https://deb.nodesource.com/setup_6.x | sudo -E bash -

> sudo apt-get install -y nodejs

> sudo npm install npm -g

#### Run front-end project:
> Navigate to Bookstore-Frontend/public_html

> sudo -H npm start

> If firefox does not open, run the front-end at localhost:3000


## Back-end

### If project not added in NetBean
Delete `build.xml` in the project root.
> File -> New Project -> Java EE -> Enterprise Application with Existing Sources -> next

>
  + Location: cloned project directory
  + Project folder: select any
  + Click next
  
> Keep default:

>
  + Server: GlassFish server 4.1.1
  + JavaEE: latest (EE7)
  + Click next

> Add application modules:
  + Add BookstoreDomain as EJB module
  + Add BookstoreService as Web module
  
  
