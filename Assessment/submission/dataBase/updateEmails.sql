UPDATE EmployeeInfo SET email = REPLACE(email, SUBSTR(email, INSTR(email, '@') + 1, INSTR(email, '.') - (INSTR(email, '@') + 1)), 'company')