SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `repository`
--

-- --------------------------------------------------------

--
-- Table structure for table `identity`
--

CREATE TABLE IF NOT EXISTS `identity` (
  `uuid` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `pluralName` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `codeName` varchar(100) COLLATE utf8_unicode_ci,
  `pluralCodeName` varchar(100) COLLATE utf8_unicode_ci,
  `payLoad` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`uuid`),
  KEY `IDENTITY_name_uuid` (`name`,`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `graph`
--

CREATE TABLE IF NOT EXISTS `graph` (
  `urr` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `uuid` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `category` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `container` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `isAbstractValue` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `maxCardinalityValueInContainer` varchar(36) COLLATE utf8_unicode_ci,
  `properClass` enum('VER','END','EDG','VIS','SUP') COLLATE utf8_unicode_ci NOT NULL,
  `contentAsXml` longtext COLLATE utf8_unicode_ci,
  PRIMARY KEY (`urr`),
  KEY `GRAPH_uuid_urr` (`uuid`,`urr`),
  KEY `categoryFK` (`category`),
  KEY `containerFK` (`container`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Constraints for table `graph`
--
ALTER TABLE `graph`
  ADD CONSTRAINT `urrFK` FOREIGN KEY (`urr`) REFERENCES `identity` (`uuid`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `graph`
  ADD CONSTRAINT `uuidFK` FOREIGN KEY (`uuid`) REFERENCES `identity` (`uuid`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `graph`
  ADD CONSTRAINT `categoryFK` FOREIGN KEY (`category`) REFERENCES `identity` (`uuid`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `graph`
  ADD CONSTRAINT `containerFK` FOREIGN KEY (`container`) REFERENCES `identity` (`uuid`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `graph`
  ADD CONSTRAINT `isAbstractValueFK` FOREIGN KEY (`isAbstractValue`) REFERENCES `identity` (`uuid`) ON DELETE NO ACTION ON UPDATE NO ACTION;  
ALTER TABLE `graph`
  ADD CONSTRAINT `maxCardinalityValueInContainerFK` FOREIGN KEY (`maxCardinalityValueInContainer`) REFERENCES `identity` (`uuid`) ON DELETE NO ACTION ON UPDATE NO ACTION;  
  
-- --------------------------------------------------------

--
-- Table structure for table `arrow`
--

CREATE TABLE IF NOT EXISTS `arrow` (
  `urr` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `category` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `properClass` enum('EDG','VIS','SUP') COLLATE utf8_unicode_ci NOT NULL,
  `fromGraph` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `toGraph` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`urr`),
  KEY `GRAPH_from_to_properClass` (`fromGraph`,`toGraph`,`properClass`,`category`),
  KEY `GRAPH_to_properClass` (`toGraph`,`properClass`,`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Constraints for table `arrow`
--
ALTER TABLE `arrow`
  ADD CONSTRAINT `arrowFK` FOREIGN KEY (`urr`) REFERENCES `graph` (`urr`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `arrow`
  ADD CONSTRAINT `fromGraphFK` FOREIGN KEY (`fromGraph`) REFERENCES `graph` (`urr`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `arrow`
  ADD CONSTRAINT `toGraphFK` FOREIGN KEY (`toGraph`) REFERENCES `graph` (`urr`) ON DELETE NO ACTION ON UPDATE NO ACTION;

  
-- --------------------------------------------------------

--
-- Table structure for table `edge`
--

CREATE TABLE IF NOT EXISTS `edge` (
  `urr` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `minCardinalityValueFromEdgeEnd` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `minCardinalityValueToEdgeEnd` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `maxCardinalityValueFromEdgeEnd` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `maxCardinalityValueToEdgeEnd` varchar(36) COLLATE utf8_unicode_ci NOT NULL, 
  `isNavigableValueFromEdgeEnd` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `isNavigableValueToEdgeEnd` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `isContainerValueFromEdgeEnd` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `isContainerValueToEdgeEnd` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `fromEdgeEnd` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `toEdgeEnd` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`urr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Constraints for table `edge`
--
ALTER TABLE `edge`
  ADD CONSTRAINT `edgeFK` FOREIGN KEY (`urr`) REFERENCES `arrow` (`urr`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `edge`
  ADD CONSTRAINT `minCardinalityValueFromEdgeEndFK` FOREIGN KEY (`minCardinalityValueFromEdgeEnd`) REFERENCES `identity` (`uuid`) ON DELETE NO ACTION ON UPDATE NO ACTION;  
ALTER TABLE `edge`
  ADD CONSTRAINT `minCardinalityValueToEdgeEndFK` FOREIGN KEY (`minCardinalityValueToEdgeEnd`) REFERENCES `identity` (`uuid`) ON DELETE NO ACTION ON UPDATE NO ACTION;  
ALTER TABLE `edge`
  ADD CONSTRAINT `maxCardinalityValueFromEdgeEndFK` FOREIGN KEY (`maxCardinalityValueFromEdgeEnd`) REFERENCES `identity` (`uuid`) ON DELETE NO ACTION ON UPDATE NO ACTION;  
ALTER TABLE `edge`
  ADD CONSTRAINT `maxCardinalityValueToEdgeEndFK` FOREIGN KEY (`maxCardinalityValueToEdgeEnd`) REFERENCES `identity` (`uuid`) ON DELETE NO ACTION ON UPDATE NO ACTION;  
ALTER TABLE `edge`
  ADD CONSTRAINT `isNavigableValueFromEdgeEndFK` FOREIGN KEY (`isNavigableValueFromEdgeEnd`) REFERENCES `identity` (`uuid`) ON DELETE NO ACTION ON UPDATE NO ACTION;  
ALTER TABLE `edge`
  ADD CONSTRAINT `isNavigableValueTeEdgeEndFK` FOREIGN KEY (`isNavigableValueTeEdgeEnd`) REFERENCES `identity` (`uuid`) ON DELETE NO ACTION ON UPDATE NO ACTION;  
ALTER TABLE `edge`
  ADD CONSTRAINT `isContainerValueFromEdgeEndFK` FOREIGN KEY (`isContainerValueFromEdgeEnd`) REFERENCES `identity` (`uuid`) ON DELETE NO ACTION ON UPDATE NO ACTION;  
ALTER TABLE `edge`
  ADD CONSTRAINT `isContainerValueToEdgeEndFK` FOREIGN KEY (`isContainerValueToEdgeEnd`) REFERENCES `identity` (`uuid`) ON DELETE NO ACTION ON UPDATE NO ACTION;  
ALTER TABLE `edge`
  ADD CONSTRAINT `fromEdgeEndFK` FOREIGN KEY (`fromEdgeEnd`) REFERENCES `graph` (`urr`) ON DELETE NO ACTION ON UPDATE NO ACTION;  
ALTER TABLE `edge`
  ADD CONSTRAINT `toEdgeEndFK` FOREIGN KEY (`toEdgeEnd`) REFERENCES `graph` (`urr`) ON DELETE NO ACTION ON UPDATE NO ACTION;  

  

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`gmodeldemo`@`localhost` PROCEDURE `getContainedInstances`(
    IN uuid VARCHAR(36) 
)
BEGIN

SELECT SQL_NO_CACHE root.urr as root, root.contentAsXml as rootxml, t1.urr as lev1,   t1.contentAsXml as xml1, t2.urr as lev2,   t2.contentAsXml as xml2, t3.urr as lev3,  t3.contentAsXml as xml3, t4.urr as lev4, t4.contentAsXml as xml4,
t5.urr as lev5, t5.contentAsXml as xml5, t6.urr as lev6,   t6.contentAsXml as xml6, t7.urr as lev7,  t7.contentAsXml as xml7
FROM repository.graph AS root
LEFT JOIN repository.graph AS t1
ON t1.container =root.urr AND t1.properClass='VER'
LEFT JOIN repository.graph AS t2
ON t2.container = t1.urr AND t2.properClass='VER'
LEFT JOIN repository.graph AS t3
ON t3.container = t2.urr AND t3.properClass='VER'
LEFT JOIN repository.graph AS t4
ON t4.container = t3.urr AND t4.properClass='VER'
LEFT JOIN repository.graph AS t5
ON t5.container = t4.urr AND t5.properClass='VER'
LEFT JOIN repository.graph AS t6
ON t6.container = t5.urr AND t6.properClass='VER'
LEFT JOIN repository.graph AS t7
ON t7.container = t6.urr AND t7.properClass='VER'
WHERE root.urr=uuid AND root.properClass='VER';

END$$

CREATE DEFINER=`gmodeldemo`@`localhost` PROCEDURE `getContainedInstanceUUIDs`(
    IN uuid VARCHAR(36) 
)
BEGIN

SELECT SQL_NO_CACHE root.urr as root, t1.urr as lev1, t2.urr as lev2, t3.urr as lev3, t4.urr as lev4, 
t5.urr as lev5, t6.urr as lev6,   t7.urr as lev7
FROM repository.graph AS root
LEFT JOIN repository.graph AS t1
ON t1.container =root.urr AND t1.properClass='VER'
LEFT JOIN repository.graph AS t2
ON t2.container = t1.urr AND t2.properClass='VER'
LEFT JOIN repository.graph AS t3
ON t3.container = t2.urr AND t3.properClass='VER'
LEFT JOIN repository.graph AS t4
ON t4.container = t3.urr AND t4.properClass='VER'
LEFT JOIN repository.graph AS t5
ON t5.container = t4.urr AND t5.properClass='VER'
LEFT JOIN repository.graph AS t6
ON t6.container = t5.urr AND t6.properClass='VER'
LEFT JOIN repository.graph AS t7
ON t7.container = t6.urr AND t7.properClass='VER'
WHERE root.urr=uuid AND root.properClass='VER';

END$$

CREATE DEFINER=`gmodeldemo`@`localhost` PROCEDURE `getDependentInstanceUUIDs`(
    IN uuid VARCHAR(36) 
)
BEGIN


SELECT graph.urr as urr
FROM repository.graph as graph
WHERE graph.category = uuid

UNION ALL

SELECT link.urr as urr
FROM repository.arrow as arrow
WHERE  arrow.from = uuid OR arrow.to = uuid;

END$$

DELIMITER ;
