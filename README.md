# Prisma IntelliJ Plugin

![Build](https://github.com/vepanimas/intellij-prisma/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/com.vepanimas.intellij.prisma.svg)](https://plugins.jetbrains.com/plugin/com.vepanimas.intellij.prisma)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/com.vepanimas.intellij.prisma.svg)](https://plugins.jetbrains.com/plugin/com.vepanimas.intellij.prisma)

# Plugin development is moved to [JetBrains plugin repository](https://github.com/JetBrains/intellij-plugins).

<!-- Plugin description -->
Adds support for [Prisma](https://www.prisma.io/) ORM and Prisma Schema Language (PSL). 
The following features are available:

* Complete Prisma Schema Language grammar support
* Semantic syntax highlighting
* Code completion
* Documentation
* Line commenter 
* Reader mode for comments with Markdown support
* Braces and quotes automatic insertion
* Rename refactoring
* Structure view
* Folding
* LSP support (since version 2022.3)
* Improved completion and resolve for generated Prisma client
<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "Prisma"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/vepanimas/intellij-prisma/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
