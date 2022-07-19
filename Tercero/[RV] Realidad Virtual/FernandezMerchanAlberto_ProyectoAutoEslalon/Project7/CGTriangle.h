#pragma once

#include <GL/glew.h>
#include "CGShaderProgram.h"

//
// CLASE: CGTriangle
//
// DESCRIPCI�N: Clase que representa un tri�ngulo descrito mediante
//              VAO para su renderizado mediante shaders
// 
class CGTriangle {
private:
	GLuint VBO;
	GLuint VAO;

public:
	CGTriangle();
	~CGTriangle();
	void Draw(CGShaderProgram* program, GLfloat posX, GLfloat posY, GLfloat size);
};