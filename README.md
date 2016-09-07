# safePass
All Java code needed for creating safe mnemonic passwords

The work at hands is a systematic approach to create better (i.e. difficult to guess) but easier to remember (for the user), passwords. 
Those passwords should be called, “Safe Mnemonic Passwords”. Regarding user passwords all the related work along with suggestions from 
security agencies, are heading towards two different directions; some of them are solely considering the entropy of a password, 
so randomly created passwords are considered strong (since they have very high entropy) although almost impossible to memorize. 
Some others are heading towards the direction of constructing a “user friendly” or “easy to remember” password not considering 
almost at all its entropy, considering passwords such as “Password1” good to use, despite its profound easiness to crack or guess 
from sophisticated software widely available today, especially designed for that purpose. Also almost all the researchers are 
suggesting users to use a different password for every application they use or website they visit. This is an obviously good 
strategy since the leakage of one password doesn’t compromise the rest, but practically inapplicable since it is humanly impossible 
to remember so many difficult passwords. The unavoidable result would be for the user(s) to write those passwords down, or store 
them somewhere electronically with a resulting big probability of losing control of this database sometime during their lifetime. 
The current essay is trying to mingle all the above, i.e. construct friendly, multiple versions and easy to remember passwords with 
high entropy and difficult to crack, the so called “safe mnemonic” passwords. For those produced passwords there is a systematic 
valuation (where a password is represented as a string) using methods and software suggested in the literature. It is also proven 
that under certain criteria such as relative entropy distance those passwords can be safely used since they cannot be discovered 
under dictionary attacks. A full working software API is also constructed embodying all the above, so it can be utilized by anyone 
to fully evaluate passwords’ strength.
