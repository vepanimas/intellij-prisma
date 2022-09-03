package com.vepanimas.intellij.prisma.ide.schema.definitions

import com.intellij.patterns.PlatformPatterns
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElementKind
import com.vepanimas.intellij.prisma.ide.schema.schema
import com.vepanimas.intellij.prisma.lang.psi.PrismaTypeDeclaration

val PRISMA_SCHEMA_KEYWORDS = schema {
    group(PrismaSchemaElementKind.KEYWORD) {
        element {
            label = "datasource"
            documentation = "The datasource block tells the schema where the models are backed."
        }
        element {
            label = "generator"
            documentation =
                "Generator blocks configure which clients are generated and how they're generated. Language preferences and binary configuration will go in here."
        }
        element {
            label = "model"
            documentation =
                "Models represent the entities of your application domain. They are defined using model blocks in the data model."
        }
        element {
            label = "enum"
            documentation =
                "Enums are defined via the enum block. You can define enums in your data model if they're supported by the datasource you use (e.g SQLite: not supported)."
        }
        element {
            label = "type"
            documentation = "Composite types are available for MongoDB only."
            pattern = PlatformPatterns.psiElement().withParent(PrismaTypeDeclaration::class.java)
        }
    }
}