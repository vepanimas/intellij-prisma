package com.vepanimas.intellij.prisma

import com.intellij.psi.util.PsiTreeUtil
import com.vepanimas.intellij.prisma.lang.psi.PrismaNamedElement
import junit.framework.TestCase

private const val CARET = "<caret>"
private const val TARGET = "<target>"

class PrismaResolveTest : PrismaTestCase() {
    fun testTypeReference() {
        checkWithTarget(
            """
            model Post {
              id        Int      @id @default(autoincrement())
              author    Us<caret>er?    @relation(fields: [authorId], references: [id])
            }
            
            model <target>User {
              id    Int     @id @default(autoincrement())
            }
        """.trimIndent()
        )
    }

    fun testTypeReferenceIgnoreConfigurationBlocks() {
        checkWithTarget(
            """
            datasource Database {
              provider = "sqlite"
            }    
                
            model Post {
              id Int
              db Dat<caret>abase
            }
            
            model <target>Database {
              id Int
            }
        """.trimIndent()
        )
    }

    private fun checkWithTarget(source: String): PrismaNamedElement {
        val targetOffset = findExpectedTargetOffset(source)
        val text = source.replace(TARGET, "")
        val file = myFixture.configureByText("schema.prisma", text)
        val reference = file.findReferenceAt(myFixture.caretOffset)
        val resolve = reference?.resolve()
        val expectedTarget =
            PsiTreeUtil.getParentOfType(file.findElementAt(targetOffset), PrismaNamedElement::class.java)
        TestCase.assertNotNull(resolve)
        TestCase.assertNotNull(expectedTarget)
        TestCase.assertEquals(expectedTarget, resolve)
        return resolve as PrismaNamedElement
    }

    private fun findExpectedTargetOffset(source: String): Int {
        val targetOffset = source.indexOf(TARGET)
        val caretOffset = source.indexOf(CARET)
        check(targetOffset >= 0 && caretOffset >= 0)
        return if (targetOffset > caretOffset) {
            targetOffset - CARET.length
        } else {
            targetOffset
        }
    }
}