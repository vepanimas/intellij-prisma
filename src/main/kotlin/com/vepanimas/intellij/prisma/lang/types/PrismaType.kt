package com.vepanimas.intellij.prisma.lang.types

import com.intellij.psi.PsiElement
import com.vepanimas.intellij.prisma.lang.PrismaConstants.PrimitiveTypes

interface PrismaType

abstract class PrismaPrimitiveType(val name: String) : PrismaType

object PrismaStringType : PrismaPrimitiveType(PrimitiveTypes.STRING)
object PrismaBooleanType : PrismaPrimitiveType(PrimitiveTypes.BOOLEAN)
object PrismaIntType : PrismaPrimitiveType(PrimitiveTypes.INT)
object PrismaFloatType : PrismaPrimitiveType(PrimitiveTypes.FLOAT)
object PrismaDateTimeType : PrismaPrimitiveType(PrimitiveTypes.DATETIME)
object PrismaJsonType : PrismaPrimitiveType(PrimitiveTypes.JSON)
object PrismaBytesType : PrismaPrimitiveType(PrimitiveTypes.BYTES)
object PrismaDecimalType : PrismaPrimitiveType(PrimitiveTypes.DECIMAL)
object PrismaBigIntType : PrismaPrimitiveType(PrimitiveTypes.BIGINT)

class PrismaUnsupportedType(name: String) : PrismaPrimitiveType(name)

object PrismaAnyType : PrismaType

abstract class PrismaTypeBase(protected val element: PsiElement) : PrismaType

class PrismaTypeImpl(val name: String, element: PsiElement) : PrismaTypeBase(element)
class PrismaCompositeType(val name: String, element: PsiElement) : PrismaTypeBase(element)

interface PrismaDecoratedType : PrismaType {
    val innerType: PrismaType

    fun unwrap(): PrismaType {
        var type = innerType
        while (type is PrismaDecoratedType) {
            type = type.innerType
        }
        return type
    }
}

class PrismaOptionalType(override val innerType: PrismaType) : PrismaDecoratedType
class PrismaListType(override val innerType: PrismaType) : PrismaDecoratedType