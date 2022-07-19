#pragma once

#include <GL/glew.h>
#include "CGObject.h"
#include "CGPiece.h"

class CGSlotCar : public CGObject {
private:
	CGPiece* pieces[2];

public:
	CGSlotCar();
	~CGSlotCar();
	virtual int GetNumPieces();
	virtual CGPiece* GetPiece(int i);
};

