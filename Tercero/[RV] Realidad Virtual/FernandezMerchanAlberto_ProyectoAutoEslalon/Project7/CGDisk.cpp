#include "CGDisk.h"
#include <GL/glew.h>
#include <math.h>
#include "CGFigure.h"

//
// FUNCIÓN: CGDisk::CGDisk()
//
// PROPÓSITO: Dibuja un disco de radio interior 'r0' (puede ser cero), 
//              radio exterior 'r1', dividido en 'p' capas y 'm' sectores.
//     
//CGDisk::CGDisk(GLint p, GLint m, GLfloat r0, GLfloat r1)
//{
//if (r0 == 0.0f)
//{
//    numFaces = 2 * (2 * m * p - m); // Number of faces
//    numVertices = 2 * (m * p + 1); // Number of vertices
//    vertices = new GLfloat[numVertices * 3];
//    indexes = new GLushort[numFaces * 3];
//
//    int verticesIndex = 0;
//    int indexesIndex = 0;
//
//    vertices[0] = 0.0f;
//    vertices[1] = 0.0f;
//    vertices[2] = 0.0;
//    verticesIndex += 3;
//
//    for (int j = 0; j < m; j++)
//    {
//        GLfloat r = (GLfloat)(r1 / p);
//        GLfloat mCos = (GLfloat)cos(glm::radians(360.0 * j / m));
//        GLfloat mSin = (GLfloat)sin(glm::radians(360.0 * j / m));
//        vertices[verticesIndex] = mCos * r;
//        vertices[verticesIndex + 1] = mSin * r;
//        vertices[verticesIndex + 2] = 0.0f;
//        verticesIndex += 3;
//
//        indexes[indexesIndex] = 0; // center
//        indexes[indexesIndex + 1] = j + 1;
//        indexes[indexesIndex + 2] = (j + 2 > m ? 1 : j + 2);
//        indexesIndex += 3;
//    }
//
//    for (int i = 2; i <= p; i++)
//    {
//        for (int j = 0; j < m; j++)
//        {
//            GLfloat r = (GLfloat)(r1 * i / p);
//            GLfloat mCos = (GLfloat)cos(glm::radians(360.0 * j / m));
//            GLfloat mSin = (GLfloat)sin(glm::radians(360.0 * j / m));
//            vertices[verticesIndex] = mCos * r;
//            vertices[verticesIndex + 1] = mSin * r;
//            vertices[verticesIndex + 2] = 0.0f;
//            verticesIndex += 3;
//        }
//    }
//
//    for (int i = 0; i < p - 1; i++)
//    {
//        for (int j = 0; j < m; j++)
//        {
//            indexes[indexesIndex] = m * i + j + 1;
//            indexes[indexesIndex + 1] = m * (i + 1) + j + 1;
//            indexes[indexesIndex + 2] = (j + 1 == m ? m * (i + 1) + 1 : m * (i + 1) + j + 2);
//            indexesIndex += 3;
//
//            indexes[indexesIndex] = m * i + j + 1;
//            indexes[indexesIndex + 1] = (j + 1 == m ? m * (i + 1) + 1 : m * (i + 1) + j + 2);
//            indexes[indexesIndex + 2] = (j + 1 == m ? m * i + 1 : m * i + j + 2);
//            indexesIndex += 3;
//        }
//    }
//
//    int base = verticesIndex / 3;
//
//    vertices[base + 0] = 0.0f;
//    vertices[base + 1] = 0.0f;
//    vertices[base + 2] = 0.0;
//    verticesIndex += 3;
//
//    for (int j = 0; j < m; j++)
//    {
//        GLfloat r = (GLfloat)(r1 / p);
//        GLfloat mCos = (GLfloat)cos(glm::radians(360.0 * j / m));
//        GLfloat mSin = (GLfloat)sin(glm::radians(360.0 * j / m));
//        vertices[verticesIndex] = mCos * r;
//        vertices[verticesIndex + 1] = mSin * r;
//        vertices[verticesIndex + 2] = 0.0f;
//        verticesIndex += 3;
//
//        indexes[indexesIndex] = base + 0; // center
//        indexes[indexesIndex + 1] = base + (j + 2 > m ? 1 : j + 2);
//        indexes[indexesIndex + 2] = base + j + 1;
//        indexesIndex += 3;
//    }
//
//    for (int i = 2; i <= p; i++)
//    {
//        for (int j = 0; j < m; j++)
//        {
//            GLfloat r = (GLfloat)(r1 * i / p);
//            GLfloat mCos = (GLfloat)cos(glm::radians(360.0 * j / m));
//            GLfloat mSin = (GLfloat)sin(glm::radians(360.0 * j / m));
//            vertices[verticesIndex] = mCos * r;
//            vertices[verticesIndex + 1] = mSin * r;
//            vertices[verticesIndex + 2] = 0.0f;
//            verticesIndex += 3;
//        }
//    }
//
//    for (int i = 0; i < p - 1; i++)
//    {
//        for (int j = 0; j < m; j++)
//        {
//            indexes[indexesIndex] = base + m * i + j + 1;
//            indexes[indexesIndex + 1] = base + (j + 1 == m ? m * (i + 1) + 1 : m * (i + 1) + j + 2);
//            indexes[indexesIndex + 2] = base + m * (i + 1) + j + 1;
//            indexesIndex += 3;
//
//            indexes[indexesIndex] = base + m * i + j + 1;
//            indexes[indexesIndex + 1] = base + (j + 1 == m ? m * i + 1 : m * i + j + 2);
//            indexes[indexesIndex + 2] = base + (j + 1 == m ? m * (i + 1) + 1 : m * (i + 1) + j + 2);
//            indexesIndex += 3;
//        }
//    }
//}
//else
//{
//    numFaces = 2 * (2 * m * p); // Number of faces
//    numVertices = 2 * (m * (p + 1)); // Number of vertices
//    vertices = new GLfloat[numVertices * 3];
//    indexes = new GLushort[numFaces * 3];
//
//    int verticesIndex = 0;
//    int indexesIndex = 0;
//
//    for (int i = 0; i <= p; i++)
//    {
//        GLfloat r = r0 + (r1 - r0) * i / p;
//        for (int j = 0; j < m; j++)
//        {
//            GLfloat mCos = (GLfloat)cos(glm::radians(360.0 * j / m));
//            GLfloat mSin = (GLfloat)sin(glm::radians(360.0 * j / m));
//            vertices[verticesIndex] = mCos * r;
//            vertices[verticesIndex + 1] = mSin * r;
//            vertices[verticesIndex + 2] = 0.0f;
//            verticesIndex += 3;
//        }
//    }
//
//    for (int i = 0; i < p; i++)
//    {
//        for (int j = 0; j < m; j++)
//        {
//            indexes[indexesIndex] = m * i + j;
//            indexes[indexesIndex + 1] = m * (i + 1) + j;
//            indexes[indexesIndex + 2] = (j + 1 == m ? m * (i + 1) : m * (i + 1) + j + 1);
//            indexesIndex += 3;
//
//            indexes[indexesIndex] = m * i + j;
//            indexes[indexesIndex + 1] = (j + 1 == m ? m * (i + 1) : m * (i + 1) + j + 1);
//            indexes[indexesIndex + 2] = (j + 1 == m ? m * i : m * i + j + 1);
//            indexesIndex += 3;
//        }
//    }
//
//    int base = verticesIndex / 3;
//
//    for (int i = 0; i <= p; i++)
//    {
//        GLfloat r = r0 + (r1 - r0) * i / p;
//        for (int j = 0; j < m; j++)
//        {
//            GLfloat mCos = (GLfloat)cos(glm::radians(360.0 * j / m));
//            GLfloat mSin = (GLfloat)sin(glm::radians(360.0 * j / m));
//            vertices[verticesIndex] = mCos * r;
//            vertices[verticesIndex + 1] = mSin * r;
//            vertices[verticesIndex + 2] = 0.0f;
//            verticesIndex += 3;
//        }
//    }
//
//    for (int i = 0; i < p; i++)
//    {
//        for (int j = 0; j < m; j++)
//        {
//            indexes[indexesIndex] = base + m * i + j;
//            indexes[indexesIndex + 1] = base + (j + 1 == m ? m * (i + 1) : m * (i + 1) + j + 1);
//            indexes[indexesIndex + 2] = base + m * (i + 1) + j;
//            indexesIndex += 3;
//
//            indexes[indexesIndex] = base + m * i + j;
//            indexes[indexesIndex + 1] = base + (j + 1 == m ? m * i : m * i + j + 1);
//            indexes[indexesIndex + 2] = base + (j + 1 == m ? m * (i + 1) : m * (i + 1) + j + 1);
//            indexesIndex += 3;
//        }
//    }
//}
//
//InitBuffers();
//}
CGDisk::CGDisk(GLint p, GLint m, GLfloat r, GLfloat l)
{
    numFaces = 2* m * (p + 1); // Number of faces
    numVertices = (m + 1) * (p + 3); // Number of vertices
    normals = new GLfloat[numVertices * 3];
    textures = new GLfloat[numVertices * 2];
    vertices = new GLfloat[numVertices * 3];
    indexes = new GLushort[numFaces * 3];

    int texturesIndex = 0;
    int normalsIndex = 0;
    int verticesIndex = 0;
    int indexesIndex = 0;

    /* northern polar cap*/
    vertices[0] = 0.0f;
    vertices[1] = 0.0f;
    vertices[2] = l;
    verticesIndex += 3;

    normals[0] = 0.0f;
    normals[1] = 0.0f;
    normals[2] = 1.0f;
    normalsIndex += 3;

    textures[0] = 0.5f;
    textures[1] = 0.5f;
    texturesIndex += 2;

    for (int j = 0; j < m; j++)
    {
        GLfloat mCos = (GLfloat)cos(glm::radians(180.0 * j / m));
        GLfloat mSin = (GLfloat)sin(glm::radians(180.0 * j / m));
        vertices[verticesIndex] = mCos * r;
        vertices[verticesIndex + 1] = mSin * r;
        vertices[verticesIndex + 2] =  l;
        verticesIndex += 3;

        normals[normalsIndex] = 0.0f;
        normals[normalsIndex + 1] = 0.0f;
        normals[normalsIndex + 2] = 1.0f;
        normalsIndex += 3;

        textures[texturesIndex] = 0.5f + mCos;
        textures[texturesIndex + 1] = 0.5f + mSin ;
        texturesIndex += 2;

        indexes[indexesIndex] = 0; // center
        indexes[indexesIndex + 1] = j + 1;
        indexes[indexesIndex + 2] = (j + 2 > m ? 1 : j + 2);
        indexesIndex += 3;
    }

    /* southern polar cap*/
    vertices[verticesIndex] = 0.0f;
    vertices[verticesIndex + 1] = 0.0f;
    vertices[verticesIndex + 2] = -l;
    verticesIndex += 3;

    normals[normalsIndex] = 0.0f;
    normals[normalsIndex + 1] = 0.0f;
    normals[normalsIndex + 2] = -1.0f;
    normalsIndex += 3;

    textures[texturesIndex] = 0.5f;
    textures[texturesIndex + 1] = 0.5f;
    texturesIndex += 2;

    for (int j = 0; j < m; j++)
    {
        GLfloat mCos = (GLfloat)cos(glm::radians(180.0 * j / m));
        GLfloat mSin = (GLfloat)sin(glm::radians(180.0 * j / m));
        vertices[verticesIndex] = -mCos * r;
        vertices[verticesIndex + 1] = mSin * r;
        vertices[verticesIndex + 2] = -l;
        verticesIndex += 3;

        normals[normalsIndex] = 0.0f;
        normals[normalsIndex + 1] = 0.0f;
        normals[normalsIndex + 2] = -1.0f;
        normalsIndex += 3;

        textures[texturesIndex] = 0.5f + mCos / 2;
        textures[texturesIndex + 1] = 0.5f - mSin / 2;
        texturesIndex += 2;

        indexes[indexesIndex] = m + 1; // center
        indexes[indexesIndex + 1] = j + m + 2;
        indexes[indexesIndex + 2] = (j + 2 > m ? m + 2 : j + m + 3);
        indexesIndex += 3;
    }

    /* body */
    for (int i = 0; i <= p; i++)
    {
        for (int j = 0; j <= m; j++)
        {
            GLfloat mCos = (GLfloat)cos(glm::radians(180.0 * j / m));
            GLfloat mSin = (GLfloat)sin(glm::radians(180.0 * j / m));
            vertices[verticesIndex] = mCos * r;
            vertices[verticesIndex + 1] = mSin * r;
            vertices[verticesIndex + 2] = l - 2 * l * i / p;
            verticesIndex += 3;

            normals[normalsIndex] = mCos;
            normals[normalsIndex + 1] = mSin;
            normals[normalsIndex + 2] = 0.0f;
            normalsIndex += 3;

            textures[texturesIndex] = ((GLfloat)j) / m;
            textures[texturesIndex + 1] = ((GLfloat)i) / p;
            texturesIndex += 2;
        }
    }

    int base = 2 * m + 2;
    for (int i = 0; i < p; i++)
    {
        for (int j = 0; j < m; j++)
        {
            indexes[indexesIndex] = base + (m + 1) * i + j;
            indexes[indexesIndex + 1] = base + (m + 1) * (i + 1) + j;
            indexes[indexesIndex + 2] = base + (m + 1) * (i + 1) + j + 1;
            indexesIndex += 3;

            indexes[indexesIndex] = base + (m + 1) * i + j;
            indexes[indexesIndex + 1] = base + (m + 1) * (i + 1) + j + 1;
            indexes[indexesIndex + 2] = base + (m + 1) * i + j + 1;
            indexesIndex += 3;
        }
    }

    InitBuffers();
}