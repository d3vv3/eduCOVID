# Eclipse Setup

1. Pull updates from master or any other branch by doing `git pull`.
2. Open Eclipse and go to `File > Open Projects from File System`.
3. Select as the `Import Source` the `educovid-backend` folder and `Select` only the Folders named as `educovid-backend`.
4. Happy development!

# Project Setup
1. If `src/main/resources` is not there, `File New -> Source Folder`. Folder name it `src/main/resources`.
2. Right click `Project Folder -> Maven -> Update Project`. Click Ok.
3. Configure Tomcat: Right click `Project folder -> Build Path -> Add Library -> Server Runtime and add Apache Tomcat v9.0.`
4. Right click `Project Folder -> Run as -> Run on Server and select Tomcat v9`.
5. (Optional) Here you might need to configure Apache Tomcat under the Apache folder and select the instalation path.
6. Happy Tomcat!

# Git

For git instructions please refer to the `README.md` in the parent directory.
