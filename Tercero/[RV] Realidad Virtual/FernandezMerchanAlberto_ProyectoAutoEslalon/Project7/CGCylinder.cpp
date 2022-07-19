#include "CGCylinder.h"
#include <GL/glew.h>
#include <math.h>
#include "CGFigure.h"

///
/// FUNCION: CGCylinder::CGCylinder(GLint p, GLint m, GLfloat r, GLfloat l)
///
/// PROPÓSITO: Construir un cilindro de radio 'r' y longitud '2*l'
/// considerando las circunferencias formadas por 'm' puntos
/// y el cuerpo del cilindro dividido en 'p' tambores.
///
/// Tapa1: (m+1) vertices, m triángulos
/// Tapa2: (m+1) vertices, m triángulos
/// Tambor: (p+1)*(m+1) vertices, 2*p*m triángulos
///
CGCylinder::CGCylinder(GLint p, GLint m, GLfloat r, GLfloat l)
{
    numFaces = 2 * m * (p + 1); // Number of faces
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
        GLfloat mCos = (GLfloat)cos(glm::radians(360.0 * j / m));
        GLfloat mSin = (GLfloat)sin(glm::radians(360.0 * j / m));
        vertices[verticesIndex] = mCos * r;
        vertices[verticesIndex + 1] = mSin * r;
        vertices[verticesIndex + 2] = l;
        verticesIndex += 3;

        normals[normalsIndex] = 0.0f;
        normals[normalsIndex + 1] = 0.0f;
        normals[normalsIndex + 2] = 1.0f;
        normalsIndex += 3;

        textures[texturesIndex] = 0.5f + mCos / 2;
        textures[texturesIndex + 1] = 0.5f + mSin / 2;
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
        GLfloat mCos = (GLfloat)cos(glm::radians(360.0 * j / m));
        GLfloat mSin = (GLfloat)sin(glm::radians(360.0 * j / m));
        vertices[verticesIndex] = mCos * r;
        vertices[verticesIndex + 1] = -mSin * r;
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
            GLfloat mCos = (GLfloat)cos(glm::radians(360.0 * j / m));
            GLfloat mSin = (GLfloat)sin(glm::radians(360.0 * j / m));
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
