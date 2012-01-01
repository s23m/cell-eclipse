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
-- Table structure for table `artifact`
--

CREATE TABLE IF NOT EXISTS `artifact` (
  `urr` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `uuid` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `category` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `container` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `isAbstractValue` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `flavor` enum('VER','END','EDG','VIS','SUP') COLLATE utf8_unicode_ci NOT NULL,
  `contentAsXml` longtext COLLATE utf8_unicode_ci,
  PRIMARY KEY (`urr`),
  KEY `ARTIFACT_uuid_urr` (`uuid`,`urr`),
  KEY `categoryFK` (`category`),
  KEY `containerFK` (`container`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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

-- --------------------------------------------------------

--
-- Table structure for table `identity`
--

CREATE TABLE IF NOT EXISTS `identity` (
  `uuid` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `pluralName` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `payLoad` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`uuid`),
  KEY `IDENTITY_name_uuid` (`name`,`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `link`
--

CREATE TABLE IF NOT EXISTS `link` (
  `urr` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `category` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `flavor` enum('EDG','VIS','SUP') COLLATE utf8_unicode_ci NOT NULL,
  `fromArtifact` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `toArtifact` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`urr`),
  KEY `ARTIFACT_from_to_flavor` (`fromArtifact`,`toArtifact`,`flavor`,`category`),
  KEY `ARTIFACT_to_flavor` (`toArtifact`,`flavor`,`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `edge`
--
ALTER TABLE `edge`
  ADD CONSTRAINT `edgeFK` FOREIGN KEY (`urr`) REFERENCES `link` (`urr`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `link`
--
ALTER TABLE `link`
  ADD CONSTRAINT `linkFK` FOREIGN KEY (`urr`) REFERENCES `artifact` (`urr`) ON DELETE NO ACTION ON UPDATE NO ACTION;

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
FROM repository.artifact AS root
LEFT JOIN repository.artifact AS t1
ON t1.container =root.urr AND t1.flavor='VER'
LEFT JOIN repository.artifact AS t2
ON t2.container = t1.urr AND t2.flavor='VER'
LEFT JOIN repository.artifact AS t3
ON t3.container = t2.urr AND t3.flavor='VER'
LEFT JOIN repository.artifact AS t4
ON t4.container = t3.urr AND t4.flavor='VER'
LEFT JOIN repository.artifact AS t5
ON t5.container = t4.urr AND t5.flavor='VER'
LEFT JOIN repository.artifact AS t6
ON t6.container = t5.urr AND t6.flavor='VER'
LEFT JOIN repository.artifact AS t7
ON t7.container = t6.urr AND t7.flavor='VER'
WHERE root.urr=uuid AND root.flavor='VER';

END$$

CREATE DEFINER=`gmodeldemo`@`localhost` PROCEDURE `getContainedInstanceUUIDs`(
    IN uuid VARCHAR(36) 
)
BEGIN

SELECT SQL_NO_CACHE root.urr as root, t1.urr as lev1, t2.urr as lev2, t3.urr as lev3, t4.urr as lev4, 
t5.urr as lev5, t6.urr as lev6,   t7.urr as lev7
FROM repository.artifact AS root
LEFT JOIN repository.artifact AS t1
ON t1.container =root.urr AND t1.flavor='VER'
LEFT JOIN repository.artifact AS t2
ON t2.container = t1.urr AND t2.flavor='VER'
LEFT JOIN repository.artifact AS t3
ON t3.container = t2.urr AND t3.flavor='VER'
LEFT JOIN repository.artifact AS t4
ON t4.container = t3.urr AND t4.flavor='VER'
LEFT JOIN repository.artifact AS t5
ON t5.container = t4.urr AND t5.flavor='VER'
LEFT JOIN repository.artifact AS t6
ON t6.container = t5.urr AND t6.flavor='VER'
LEFT JOIN repository.artifact AS t7
ON t7.container = t6.urr AND t7.flavor='VER'
WHERE root.urr=uuid AND root.flavor='VER';

END$$

CREATE DEFINER=`gmodeldemo`@`localhost` PROCEDURE `getDependentInstanceUUIDs`(
    IN uuid VARCHAR(36) 
)
BEGIN


SELECT artifact.urr as urr
FROM repository.artifact as artifact
WHERE artifact.category = uuid

UNION ALL

SELECT link.urr as urr
FROM repository.link as link
WHERE  link.fromArtifact = uuid OR link.toArtifact = uuid;

END$$

DELIMITER ;
