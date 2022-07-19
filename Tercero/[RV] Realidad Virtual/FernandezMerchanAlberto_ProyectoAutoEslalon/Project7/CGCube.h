#pragma once

#include <GL/glew.h>
#include "CGFigure.h"

//
// CLASE: CGCube
//
// DESCRIPCIÓN: Representa un cubo de lado 2·s. 
// 
class CGCube : public CGFigure {
private:
	int identificador;
public:
	int getId();
	void setId(int id);
	CGCube(GLfloat s);
	CGCube(GLfloat s, GLfloat h, GLfloat r0);
	CGCube(GLfloat s, GLfloat h, GLfloat r0, GLfloat r1);
};
